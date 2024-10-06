@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.login.ui


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.common.navigation.NavigationRoute.Home
import com.mito.common.navigation.model.HomeNavigationData
import com.mito.components.MitoButtonSheet
import com.mito.components.MitoBottomSheet
import com.mito.components.PrimaryButton
import com.mito.components.MitoTextBasic
import com.mito.components.MitoTextField
import com.mito.core.navigation.Screen
import com.mito.database.data.dao.UserDao
import com.mito.database.data.entity.UserEntity
import com.mito.login.R
import com.mito.login.data.DummyLoginDataSourceImpl
import com.mito.login.data.DummyLoginRepositoryImpl
import com.mito.login.data.UserDataSourceImpl
import com.mito.login.domain.DummyLoginUseCase
import com.mito.network.dummy_login.data.LoginService
import com.mito.network.dummy_login.data.request.LoginRequest
import com.mito.network.dummy_login.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import kotlin.system.exitProcess

class LoginScreen : Screen {
    override val route: String = NavigationReferences.LoginReference.getRoute()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<LoginViewModel>()
        val status by viewModel.status.collectAsState()
        //Add BackHandler action here

        BackHandler {
            viewModel.showCloseDialog()
        }
        if (status.sheetValue != Hidden) {
            MitoBottomSheet(
                mitoButtonSheet = MitoButtonSheet.CloseAppMitoButtonSheet(
                    onDismissRequest = { viewModel.hideCloseDialog() },
                    onDismiss = { viewModel.hideCloseDialog() },
                    onConfirm = { exitProcess(0) },
                    sheetValue = status.sheetValue
                )
            )
        }
        LoginScreen(navController, viewModel)
    }
}

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.systemBars
                    .asPaddingValues()
                    .calculateTopPadding(),
                bottom = WindowInsets.systemBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ),
        color = colorResource(id = R.color.surface_login_and_register_color)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(
                    Color.White
                )
        ) {
            Login(viewModel, navController)
        }
    }
}

@Composable
fun Login(viewModel: LoginViewModel, navController: NavHostController) {

    val event by viewModel.event.collectAsState()
    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.8f))
        MainImage(Modifier.align(Alignment.CenterHorizontally), navController)
        Spacer(modifier = Modifier.weight(1f))
        EmailItem(status) { viewModel.onLoginChanged(it, status.password) }
        PasswordItem(status) { viewModel.onLoginChanged(status.username, it) }
        Spacer(modifier = Modifier.weight(0.1f))
        ForgotPassword(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { }
                .padding(6.dp)
        )
        SignUp(modifier = Modifier
            .align(Alignment.End)
            .clickable { }
            .padding(6.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        LoginButton(status) { viewModel.login() }
    }
    viewModel.isLoading(event)
    when (event) {
        is Event.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.8f))
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is Event.Success -> {
            MitoBottomSheet(
                getButtonSheet(
                    text = "${(event as Event.Success).message} ${status.username}",
                    status = status,
                    onDismissRequest = { viewModel.clearEvent() }
                ) {
                    status.userId?.let { userId ->
                        navController.navigate(Home(HomeNavigationData(userId)).navigateTo())
                    }
                    viewModel.clearEvent()
                }
            )
        }

        is Event.Error -> {
            MitoBottomSheet(
                mitoButtonSheet =
                MitoButtonSheet.InfoMitoButtonSheet(
                    title = R.string.home_screen_full_application,
                    messageString = (event as Event.Error).message,
                    onDismissRequest = { viewModel.clearEvent() },
                    sheetValue = status.sheetValue
                )
            )
        }

        else -> {}
    }
}

fun getButtonSheet(
    text: String,
    status: Status,
    onDismissRequest: () -> Unit,
    onSuccess: () -> Unit,
): MitoButtonSheet.ContinueMitoButtonSheet =
    MitoButtonSheet.ContinueMitoButtonSheet(
        title = R.string.welcome_title_dialog,
        messageString = text,
        onAccept = onSuccess,
        onDismissRequest = onDismissRequest,
        sheetValue = status.sheetValue
    )

@Composable
fun LoginButton(
    status: Status,
    onLoginSelected: () -> Unit,
) {
    PrimaryButton(
        action = { onLoginSelected() },
        isEnabled = status.loginEnable && status.isLoading.not(),
        text = R.string.login_text_field_intro_button
    )
}

@Composable
fun SignUp(modifier: Modifier) {
    MitoTextBasic(
        text = stringResource(id = R.string.login_text_sign_up),
        modifier = modifier
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    MitoTextBasic(
        text = stringResource(id = R.string.login_text_intro_forgot_password),
        modifier = modifier
    )
}

@Composable
fun PasswordItem(
    status: Status,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.password,
        onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_password)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        showTrailingIcon = true,
        showPassword = true,
        enabled = status.isLoading.not()
    )
}

@Composable
fun EmailItem(
    status: Status,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.username,
        onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_email)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        enabled = status.isLoading.not()
    )
}

@Composable
fun MainImage(modifier: Modifier, navController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.main_image),
        contentDescription = stringResource(id = R.string.login_text_intro_header_content_description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .border(
                BorderStroke(4.dp, Color(0xFFE96D34)),
                CircleShape
            )
            .size(200.dp)
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {

    val loginService = FakeLoginService()
    val repository = DummyLoginRepositoryImpl(
        DummyLoginDataSourceImpl(loginService),
        UserDataSourceImpl(FakeUserDao))
    val dummyLoginUseCase = DummyLoginUseCase(repository)

    LoginScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = LoginViewModel(dummyLoginUseCase)
    )
}

class FakeLoginService : LoginService {

    private val fakeMessage = "fake_token"
    override suspend fun login(
        loginRequest: LoginRequest,
        contentType: String,
    ): Response<LoginResponse> {
        // Implementar lógica de login falso para la vista previa
        return Response.success(LoginResponse(fakeMessage, "ok"))
    }
}

object FakeUserDao : UserDao {
    override fun getAll(): Flow<List<UserEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserId(email: String, password: String): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(user: UserEntity) {
        TODO("Not yet implemented")
    }
}
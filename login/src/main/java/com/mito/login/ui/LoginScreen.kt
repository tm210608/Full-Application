package com.mito.login.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mito.login.R
import com.mito.login.ui.LoginViewModel.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
      Login(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val event = viewModel.event.collectAsState()
    val status = viewModel.status.collectAsState()
    val loginEnable : Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if(isLoading) {
        Box(
            Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier) {
            MainImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailItem(status.value.username) { viewModel.onLoginChanged(it, status.value.password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordItem(status.value.password) { viewModel.onLoginChanged(status.value.username,it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
    when (event.value) {
        is Event.Success -> {
            ResultDialog(
                viewModel = viewModel,
                text = "${(event.value as Event.Success).message} ${status.value.username}"
            )
        }

        is Event.Error -> {
            ResultDialog(
                viewModel = viewModel,
                text = (event.value as Event.Error).message
            )
        }
        else -> {}
    }
}

@Composable
fun ResultDialog(viewModel: LoginViewModel, text: String) {
    Dialog(onDismissRequest = {
        viewModel.clearEvent()
    }) {
        Text(
            text = text, modifier = Modifier
                .background(Color.White)
                .padding(40.dp)
        )
    }
}


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected()},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE96D34),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFD6A28A),
            disabledContentColor = Color.White
        ),
        enabled = loginEnable,
        shape = RoundedCornerShape(8.dp)
    ){
        Text(text = stringResource(id = R.string.login_text_field_intro_button))
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.login_text_intro_forgot_password),
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color= Color(0xFFE96D34)
    )
}

@Composable
fun PasswordItem(password: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_password)) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF21241D),
            unfocusedTextColor = Color(0xFF394132),
            focusedContainerColor = Color(0xFF9AE485),
            unfocusedContainerColor = Color(0xFF9AE485),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailItem(email: String, onTextFieldChanged: (String) -> Unit) {


    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text( text = stringResource(id = R.string.login_text_field_intro_email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null)},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF21241D),
            unfocusedTextColor = Color(0xFF394132),
            focusedContainerColor = Color(0xFF9AE485),
            unfocusedContainerColor = Color(0xFF9AE485),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun MainImage(modifier: Modifier) {
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


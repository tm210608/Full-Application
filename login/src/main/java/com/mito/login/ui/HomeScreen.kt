package com.mito.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mito.login.R

@Composable
fun HomeScreen() {
    Card (
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(Color(0x7CC4F0B8)),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        shape = RoundedCornerShape(25.dp),
    ){
        InitialScreen(modifier = Modifier)
    }
}

@Composable
fun InitialScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTextHomeScreen()
        Spacer(modifier = Modifier.padding(20.dp))
        ImageScreen()
        Spacer(modifier = Modifier.padding(15.dp))
        IntroButtonScreen()
    }
}

@Composable
fun HeaderTextHomeScreen() {
    Text(
        text = stringResource(id = R.string.home_screen_full_application),
        fontFamily = FontFamily.Monospace,
        style = MaterialTheme.typography.labelMedium,
        fontSize = 25.sp,
        letterSpacing = 3.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun IntroButtonScreen() {
    Button(
        onClick = {},
        modifier = Modifier
            .height(50.dp)
            .padding(8.dp)
            .size(120.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE96D34),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFD6A28A),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
        )
    {
        Text(text = stringResource(id = R.string.home_screen_button_text))
    }
}

@Composable
fun ImageScreen() {
    Image(
        painter = painterResource(id = R.drawable.freepik_proyecto_sin_titulo_20240815101640phtl),
        contentDescription = stringResource(id = R.string.home_screen_image_header_content_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(300.dp)
            .clip(CircleShape),
        alignment = Alignment.Center,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
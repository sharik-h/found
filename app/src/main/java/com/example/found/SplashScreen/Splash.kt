package com.example.found.SplashScreen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.found.LoginNavigation.Screen
import com.example.found.R


@Composable
fun Splash(navHostController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaanim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(2000)
        navHostController.popBackStack()
        navHostController.navigate(Screen.search.route)
    }
    SplashPreview(alpha = alphaanim.value)
}

@Composable
fun SplashPreview(alpha: Float) {
    Column(modifier = Modifier
        .fillMaxSize()
        .alpha(alpha = alpha)
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            val logo: Painter = painterResource(id = R.drawable.logo)
            Image(painter = logo, contentDescription = "", modifier = Modifier.size(100.dp))
        
        Text(text = "Found", fontSize = 40.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun prev() {
    SplashPreview( 1f)
}
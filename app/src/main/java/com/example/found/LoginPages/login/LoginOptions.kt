package com.example.found.LoginPages.login

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.found.LoginNavigation.Screen
import com.example.found.LoginPages.LoginWith.LoginWithGoogle
import com.example.found.R
import kotlin.math.round

@Composable
fun LoginOptions(navHostController: NavHostController) {

    var gclick = false
    var text = "Continue with google"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF78B3B3))
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Log in ",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Welcome to Found",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Save a place that you like and make it easier to find the place when you wish to visit.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                val context = LocalContext.current
                GoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(50)),
                ) { context.startActivity(Intent(context, LoginWithGoogle::class.java))}
                Button(
                    onClick = { navHostController.navigate(route = Screen.EnterUserDetails.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 20.dp),
                    colors = buttonColors(backgroundColor = Color.Transparent),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Create Acccount",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    val rember= rememberNavController()
    LoginOptions(rember)
}
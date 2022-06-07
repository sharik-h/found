package com.example.found.LoginPages.login

import android.content.Intent
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.example.found.ui.theme.*

@Composable
fun LoginOptions(navHostController: NavHostController) {

    val nunito_bold = Font(R.font.nunito_sans_bold)
    val nunito_sans = Font(R.font.nunito_sans)
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(teritary90)
            .padding(20.dp)
    ) {
        Text(
            text = "Welcome to Found",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = teritary40,
            fontFamily = FontFamily(nunito_bold)
        )
        Text(
            text = "save any location you like, so you dont have to search for it again and again, and find the route with one click.",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = teritary40,
            fontFamily = FontFamily(nunito_sans)
        )
        Row(
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            Button(
                onClick = { navHostController.navigate(route = Screen.loginWithMobile.route) },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp)
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(end = 4.dp),
                colors =  buttonColors(teritary40),
                shape = RoundedCornerShape(30),
            ) {
                Text(
                    text = "Log In",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(nunito_sans)
                )
            }
            GoogleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(45.dp)
                    .padding(start = 4.dp)
                    .clip(RoundedCornerShape(30)),
            ) { context.startActivity(Intent(context, LoginWithGoogle::class.java)) }
        }
        Divider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Text(
                text = "If you don`t have and account you can signup here, ",
                fontFamily = FontFamily(nunito_sans),
                color = teritary40,
                fontSize = 12.sp
            )
            Text(
                text = "click here.",
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = teritary10,
                fontFamily = FontFamily(nunito_sans),
                modifier = Modifier
                    .clickable {
                        navHostController.navigate(route = Screen.enterUserDetails.route)
                    },
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun preview() {
    val rember= rememberNavController()
    LoginOptions(rember)
}
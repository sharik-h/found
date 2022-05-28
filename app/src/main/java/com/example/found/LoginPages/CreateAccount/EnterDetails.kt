package com.example.found.LoginPages.CreateAccount

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.found.LoginPages.Auth.Authenticate


@Composable
fun EnterUserDetails(navHostController: NavHostController) {

    val context = LocalContext.current

    var name by remember{ mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF78B3B3))
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentPadding = PaddingValues(start = 0.dp, end = 10.dp, top = 10.dp),
            elevation = 0.dp,
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick ={
                        context.startActivity(Intent(context, Authenticate::class.java)
                            .putExtra("name",name)
                            .putExtra("phone",phone)
                            .putExtra("password", password))

                             },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Text(
                        text = "Send OTP",
                        color = Color(0xFF78B3B3),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                }
            }

        }
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Create Account",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "NAME",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                value = name,
                onValueChange = { it -> name = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )
            Text(
                text = "PHONE NUMBER",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold),
                value = phone,
                onValueChange = { it -> phone = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ))
            Text(
                text = "PASSWORD",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            TextField(
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                value = password,
                onValueChange = { it -> password = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val rem = rememberNavController()
    EnterUserDetails(rem)
}
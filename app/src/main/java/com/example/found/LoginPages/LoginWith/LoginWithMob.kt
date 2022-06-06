package com.example.found.LoginPages.LoginWith

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.found.LoginPages.Auth.Authenticate


@Composable
fun LoginWithMob() {

    var phone by remember{ mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF78B3B3))
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentPadding = PaddingValues(start = 0.dp, end = 10.dp, top = 10.dp),
            elevation = 0.dp,
        ) {
            TextButton(onClick = {  }) {
                Image(imageVector = Icons.Default.ArrowBack, contentDescription = "", colorFilter = ColorFilter.tint(
                    Color.White), modifier = Modifier.size(30.dp))
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { context.startActivity(Intent(context, Authenticate::class.java).putExtra("phone",phone)) },
                    modifier = Modifier.height(50.dp).width(160.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Text(
                        text = "SEND OTP",
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
                text = "Login",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Mobile Number",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = phone,
                onValueChange = { it -> phone = it },
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
fun Preview1() {
    LoginWithMob()
}
package com.example.found.LoginPages.LoginWith

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.found.LoginPages.Auth.Authenticate
import com.example.found.R
import  androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.found.ui.theme.*


@Composable
fun LoginWithMob(navHostController: NavHostController) {
    val nunitoBold = Font(R.font.nunito_sans_bold)
    val nunitoSans = Font(R.font.nunito_sans)
    var phone by remember{ mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(secondary95)
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentPadding = PaddingValues(start = 0.dp, end = 10.dp, top = 10.dp),
            elevation = 0.dp,
        ) {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(teritary40),
                    modifier = Modifier.size(30.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if(phone.length == 10) {
                            context.startActivity(
                                Intent(context, Authenticate::class.java)
                                    .putExtra("phone", phone))
                        }else {
                            Toast.makeText(context, "Something Wrong with the number", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .width(140.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(backgroundColor = teritary40),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "",
                        Modifier.size(20.dp),
                        tint = teritary100
                    )
                    Text(
                        text = "Send OTP",
                        color = teritary100,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(nunitoBold)
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
                fontSize = 30.sp,
                color = teritary40,
                fontFamily = FontFamily(nunitoBold)
            )
            Text(
                text = "Enter your mobile number",
                fontSize = 15.sp,
                color = teritary40,
                fontFamily = FontFamily(nunitoBold)
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = teritary40,
                    unfocusedIndicatorColor = teritary10,
                    cursorColor = teritary40,
                ),
                textStyle = TextStyle(
                    color = teritary40,
                    fontFamily = FontFamily(nunitoSans),
                    fontSize = 20.sp,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (phone.length == 10) {
                            context.startActivity(
                                Intent(context, Authenticate::class.java)
                                    .putExtra("phone", phone))
                        }else {
                            Toast.makeText(context, "Something wrong with the number.", Toast.LENGTH_SHORT).show()
                        }
                    }
                ),
                singleLine = true,
                isError = phone.length > 10
            )
        }
    }
}


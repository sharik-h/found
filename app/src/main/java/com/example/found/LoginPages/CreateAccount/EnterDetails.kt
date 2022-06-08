package com.example.found.LoginPages.CreateAccount

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.found.LoginPages.Auth.Authenticate
import com.example.found.R
import com.example.found.ui.theme.secondary95
import com.example.found.ui.theme.teritary10
import com.example.found.ui.theme.teritary100
import com.example.found.ui.theme.teritary40


@Composable
fun EnterUserDetails(navHostController: NavHostController) {

    val context = LocalContext.current
    val nunitoSans = Font(R.font.nunito_sans)
    val nunitoBold = Font(R.font.nunito_sans_bold)
    var name by remember{ mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                        if (phone.length == 10 && name.length >= 1) {
                            context.startActivity(
                                Intent(context, Authenticate::class.java)
                                    .putExtra("name", name)
                                    .putExtra("phone", phone)
                                    .putExtra("password", password)
                            )
                        }
                    },
                    modifier = Modifier.height(45.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(backgroundColor = teritary40),
                    elevation = ButtonDefaults.elevation(2.dp)
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
                text = "Create Account",
                fontSize = 30.sp,
                color = teritary40,
                fontFamily = FontFamily(nunitoBold)
            )
            Text(
                text = "Name",
                fontSize = 20.sp,
                color = teritary40,
                fontFamily = FontFamily(nunitoBold)
            )
            OutlinedTextField(
                textStyle = TextStyle(
                    color = teritary40,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(nunitoSans)),
                value = name,
                onValueChange = { name = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = teritary40,
                    unfocusedIndicatorColor = teritary40,
                    cursorColor = teritary40
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            Text(
                text = "Phone number",
                fontSize = 20.sp,
                color = teritary40,
                fontFamily = FontFamily(nunitoBold)
            )
            OutlinedTextField(
                textStyle = TextStyle(
                color = teritary40,
                fontSize = 20.sp,
                fontFamily = FontFamily(nunitoSans)),
                value = phone,
                onValueChange = { phone = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor =teritary40,
                    unfocusedIndicatorColor = teritary40,
                    cursorColor = teritary40
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if ( phone.length == 10 && name.length >= 10){
                            context.startActivity(Intent(context, Authenticate::class.java)
                                .putExtra("name", name)
                                .putExtra("phone", phone)
                            )
                        }
                    }
                )
            )
        }
    }
}

@Composable
fun costomModel(
    title : String,
    value :(String) -> Unit
) {
    val nunitoBold = Font(R.font.nunito_sans_bold)
    var detail by remember{ mutableStateOf("") }
    Text(
        text = title,
        fontSize = 20.sp,
        color = Color.White,
        fontFamily = FontFamily(nunitoBold)
    )
    TextField(
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold),
        value = detail,
        onValueChange = { it ->
            detail = it
                        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val rem = rememberNavController()
    EnterUserDetails(rem)
}
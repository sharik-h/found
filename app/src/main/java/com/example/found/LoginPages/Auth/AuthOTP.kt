package com.example.found.LoginPages.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AuthOtp() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF78B3B3))) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentPadding = PaddingValues(start = 0.dp, end = 10.dp, top = 10.dp),
            elevation = 0.dp,
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Default.ArrowBack, contentDescription = "", colorFilter = ColorFilter.tint(
                    Color.White), modifier = Modifier.size(30.dp))
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(50.dp)
                        .width(130.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(10.dp)
                ) {
                    Text(
                        text = "VERIFY",
                        color = Color(0xFF78B3B3),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                }
            }

        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text(
                text = "Enter OTP",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
            var otp by remember { mutableStateOf("") }
            otpField() { getotp ->
                otp = getotp
            }

        }
    }
}


@Composable
fun otpField(
    onFilled: (code: String) -> Unit
) {
    var otp: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(6) {
            temp.add(FocusRequester())
        }
        temp
    }
    Row(
        modifier = Modifier
//            .height(60.dp)
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        (0 until 6).forEach { index ->
            TextField(
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .width(45.dp)
                    .focusOrder(focusRequester = focusRequesters[index]) {
                        focusRequesters[index + 1].requestFocus()
                    },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    textColor = Color.White),
                value = otp.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                onValueChange = { value: String ->
                    if (focusRequesters[index].freeFocus()) {
                        val temp = otp.toMutableList()
                        if (value == "") {
                            if (temp.size > index) {
                                temp.removeAt(index = index)
                                otp = temp
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if (otp.size > index) {
                                temp[index] = value.getOrNull(0) ?: ' '
                            } else {
                                temp.add(value.getOrNull(0) ?: ' ')
                                otp = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    ?: onFilled(
                                        otp.joinToString(separator = "")
                                    )
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview() {
    AuthOtp()
}
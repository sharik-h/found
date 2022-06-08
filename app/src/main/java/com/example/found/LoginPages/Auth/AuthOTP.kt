package com.example.found.LoginPages.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.found.MainActivity
import com.example.found.R
import com.example.found.ui.theme.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class Authenticate: ComponentActivity() {

    lateinit var Sotp: String
    var sendStatus = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val password = intent.getStringExtra("password")


        setContent {
            AuthOtp()
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Toast.makeText(applicationContext, "Verified", Toast.LENGTH_SHORT).show()
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        sendStatus.value = -1
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(p0, p1)
                        Sotp = p0
                        sendStatus.value = 1
                    }
                })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun SignInWithCred(otp: String) {
        val credential = PhoneAuthProvider.getCredential(Sotp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    finishAffinity()
                    this.startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @Composable
    fun AuthOtp() {
        var otp by remember { mutableStateOf("") }
        val nunitoBold = Font(R.font.nunito_sans_bold)
        val nunitosans = Font(R.font.nunito_sans)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(secondary95)
        ) {
            TopAppBar(
                backgroundColor = Color.Transparent,
                contentPadding = PaddingValues(start = 0.dp, end = 10.dp, top = 10.dp),
                elevation = 0.dp,
            ) {
                TextButton(onClick = { finish() }) {
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
                        onClick = { SignInWithCred(otp = otp) },
                        modifier = Modifier
                            .height(45.dp)
                            .width(110.dp),
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(backgroundColor = teritary40),
                        elevation = ButtonDefaults.elevation(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "",
                            Modifier.size(20.dp),
                            tint = teritary100
                        )
                        Text(
                            text = "Verify",
                            color = teritary100,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(nunitoBold),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(
                    text = "Enter OTP",
                    fontSize = 30.sp,
                    fontFamily = FontFamily(nunitoBold),
                    color = teritary40,
                    modifier = Modifier.padding(10.dp)
                )
                otpField() { getotp ->
                    otp = getotp
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {

                    if (sendStatus.value == 0) {
                        Text(
                            text = "Sending OTP",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(nunitosans)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .height(16.dp)
                                .width(16.dp),
                            strokeWidth = 2.dp,
                            color = dimblue
                        )
                    }else if(sendStatus.value == -1) {
                        Text(
                            text = "Sending OTP failed",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(nunitosans)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .width(16.dp)
                                .height(16.dp),
                            tint = dimred
                        )
                    }else if (sendStatus.value == 1){
                        Text(
                            text = "OTP send",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(nunitosans)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .width(16.dp)
                                .height(16.dp),
                            tint = dimgreen
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun otpField(
        onFilled: (code: String) -> Unit
    ) {
        val nunitoBold = Font(R.font.nunito_sans_bold)
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
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            (0 until 6).forEach { index ->
                TextField(
                    textStyle = TextStyle(
                        color = teritary40,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(nunitoBold)
                    ),
                    modifier = Modifier
                        .width(45.dp)
                        .focusOrder(focusRequester = focusRequesters[index]) {
                            focusRequesters[index + 1].requestFocus()
                        },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = teritary40,
                        unfocusedIndicatorColor = teritary40,
                        textColor = teritary40,
                        cursorColor = teritary40
                    ),
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
                    ),
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}




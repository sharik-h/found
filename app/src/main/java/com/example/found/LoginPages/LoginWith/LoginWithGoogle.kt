package com.example.found.LoginPages.LoginWith

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.found.MainActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginWithGoogle: ComponentActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var getResult: ActivityResultLauncher<Intent>
    val RCSIGNIN = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN )
            .requestIdToken("394456972537-aiclrb3aomvqh92gcrv7arhhaaq5v6q5.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient (this, gso)
        auth = FirebaseAuth.getInstance()




    }

    override fun onStart() {
        super.onStart()

         getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if ( 2 == RCSIGNIN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                if (task.isSuccessful) {
                    try {
                        val account = task.getResult(ApiException::class.java)!!
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        Toast.makeText(this,"Sign in Failed please try again later", Toast.LENGTH_SHORT).show()
                    }
                }else Log.d("exceptions", task.exception.toString())
            }
        }

        val signInIntent = googleSignInClient.signInIntent
        getResult.launch(signInIntent)
    }



    @Composable
    fun sasi() {
        Column(Modifier.fillMaxSize()) {
            Button(onClick = {
                val signInIntent = googleSignInClient.signInIntent
                getResult.launch(signInIntent)
            }) {
                Text(text = "click me")
            }
        }
    }



    private fun firebaseAuthWithGoogle ( idToken : String ) {
        val credential = GoogleAuthProvider.getCredential ( idToken , null )
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if ( task.isSuccessful ) {
                    finishAffinity()
                    this.startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Log.d( "SignInActivity" , " signInWithCredential : failure " , task.exception )
                }
            }
    }
}
package com.example.found.SearchPage

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.found.Maps.MapsActivity
import com.example.found.R
import com.example.found.data.firestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.livedata.observeAsState
import com.google.firebase.firestore.auth.User


@Composable
fun Searchpage(viewModel: firestoreViewModel) {
    val openDialog = remember { mutableStateOf(false)  }
    showAlertDailogue(openDialog = openDialog)

    val database = Firebase.firestore
    val currentUid = FirebaseAuth.getInstance().currentUser!!.uid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFE9FD))
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.5.dp,
            contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
        ) {
            TextButton(onClick = { /*TODO*/ } )
            {
                Image(imageVector = Icons.Default.Search, contentDescription = "", modifier = Modifier.size(35.dp) )
            }
            Text(
                text = "Found",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            TextButton(onClick = { /*TODO*/ }) {
                val usericon: Painter = painterResource(id = R.drawable.user_icon)
                Image(painter = usericon, contentDescription = "", modifier = Modifier.size(40.dp))
            }
        }


        viewModel.getData()
        val userDetails by viewModel.userDetails.observeAsState(initial = emptyList())
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),

        ) {
            items(items = userDetails) {
                UserOption(ItemName = it.name.toString())
            }
        }
        TextButton(
                onClick = { openDialog.value = true },
        modifier = Modifier.size(90.dp)
        ) {
        Image(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .padding(5.dp)
        )
    }
    }
}

@Composable
fun showAlertDailogue(openDialog: MutableState<Boolean>) {
    var name by remember { mutableStateOf("") }
    var openDialog = openDialog
    val context = LocalContext.current
    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                Button(onClick = {
                    if(name.length >= 1) {
                        context.startActivity(Intent(context, MapsActivity::class.java)
                            .putExtra("name",name))
                        Log.d("name",name)
                        name = ""
                        openDialog.value = false
                    }

                }) {
                    Text(text = "Next")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                    name = ""
                }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Enter the name of location")},
            text = {
                TextField(
                    value = name,
                    onValueChange = { it -> name = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                    )
                )
            }
        )
    }

}

@Composable
fun UserOption(
    ItemName: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = ItemName)
      }

}

@Preview(showBackground = true)
@Composable
fun prec() {
//    Searchpage()
    UserOption(ItemName = "sharikh")
}
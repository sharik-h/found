package com.example.found.SearchPage

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.found.ui.theme.*


@Composable
fun Searchpage(viewModel: firestoreViewModel) {
    val openDialog = remember { mutableStateOf(false)  }
    showAlertDailogue(openDialog = openDialog)

    val nunito_sans = Font(R.font.nunito_sans)
    val nunito_bold = Font(R.font.nunito_sans_bold)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = secondary95)
    ) {
        TopAppBar(
            backgroundColor = secondary90,
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
                fontFamily = FontFamily(nunito_bold),
                fontSize = 30.sp
            )
            TextButton(onClick = { /*TODO*/ }) {
                val usericon: Painter = painterResource(id = R.drawable.user_icon)
                Image(painter = usericon, contentDescription = "", modifier = Modifier.size(40.dp))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        viewModel.getData()
        val userDetails by viewModel.userDetails.observeAsState(initial = emptyList())
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
        ) {
            items(items = userDetails) {
                UserOption(ItemName = it.name.toString())
            }
        }


    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = {  openDialog.value = true },
            modifier = Modifier
                .height(60.dp)
                .width(150.dp),
            shape = RoundedCornerShape(30),
            backgroundColor = secondary90,
            elevation = FloatingActionButtonDefaults.elevation(3.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val direction = painterResource(id = R.drawable.directions)
                Image(painter = direction, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                    Text(
                    text = "Add Route",
                    color = Color.Black,
                    fontFamily = FontFamily(nunito_sans),
                    fontWeight = FontWeight.Bold,

                    )
            }
        }
    }
}

@Composable
fun showAlertDailogue(openDialog: MutableState<Boolean>) {
    val nunito_sans = Font(R.font.nunito_sans)
    val nunito_bold = Font(R.font.nunito_sans_bold)

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
                        name = ""
                        openDialog.value = false
                    }
                },
                    modifier = Modifier
                        .height(40.dp)
                        .width(95.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(secondary90),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "",modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Next", fontSize = 15.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    name = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "", modifier = Modifier.size(18.dp), tint = secondary10)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cancel", color = secondary10, fontSize = 15.sp)
                }
            },
            title = {
                Text(text = "Name the place", fontFamily = FontFamily(nunito_bold), fontSize = 18.sp, )
                    },
            text = {
                OutlinedTextField(
                    value = name,
                    onValueChange = { it -> name = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = primary40,
                        cursorColor = primary40
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily(nunito_sans), fontSize = 18.sp),
                    singleLine = true,
                )
            }
        )
    }

}

@Composable
fun UserOption(
    ItemName: String
) {

    val nunito = Font(R.font.nunito_sans)

    Box(
        modifier = Modifier
            .padding(vertical = 1.dp, horizontal = 10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20))
            .background(secondary100),
    ) { Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
           val red = Color(0xFFea4335)
            val blue = Color(0xFF4285f4)
            val green = Color(0xFF34a853)
            val yellow = Color(0xFFfbbc05)
            val colors = arrayOf(red,blue,green,yellow)
            val color = colors.random()
            Icon(
                painter = painterResource(id = R.drawable.back_circle),
                contentDescription = "",
                tint = color,
                modifier = Modifier
                    .padding(start = 10.dp)

            )
            val gps = painterResource(id = R.drawable.gps)
            Icon(painter = gps, contentDescription = "", tint = Color.White, modifier = Modifier.padding(start = 10.dp))
        }
        Text(
            text = ItemName,
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1f),
            fontSize = 20.sp,
            fontFamily = FontFamily(nunito),
            textAlign = TextAlign.Start
        )
    }
    }

}

@Preview(showBackground = true)
@Composable
fun prec() {
   // UserOption(ItemName = "sharikh")
    val openDialog = remember { mutableStateOf(true)  }
    showAlertDailogue(openDialog = openDialog)
}
package com.example.found.SearchPage

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import com.example.found.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun LocationListPage(viewModel: firestoreViewModel) {
    val openDialog = remember { mutableStateOf(false)  }
    var isSearchVisible by remember { mutableStateOf(false)  }
    var search by remember { mutableStateOf("") }
    addAlertDailod(openDialog = openDialog )

    val nunito_sans = Font(R.font.nunito_sans)
    val nunito_bold = Font(R.font.nunito_sans_bold)
    val circularClose = painterResource(id = R.drawable.circle_close)

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
            TextButton(onClick = { isSearchVisible = !isSearchVisible } )
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


        if (isSearchVisible) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(secondary90)
                    .padding(10.dp),
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = {  search = it },
                    shape = RoundedCornerShape(30),
                    textStyle = TextStyle(fontFamily = FontFamily(nunito_sans)),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    modifier = Modifier
                        .height(40.dp)
                        .weight(0.8f)
                        .clip(RoundedCornerShape(30))
                        .background(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        IconButton(onClick = { search = "" }) {
                            Icon(
                                painter = circularClose,
                                contentDescription = ""
                            )
                        }
                    },
                )
                TextButton(onClick = { isSearchVisible = false }) {
                    Text(
                        text = "Cancel",
                        fontFamily = FontFamily(nunito_bold),
                        color = Color.Black
                    )
                }
            }
        }

            Spacer(modifier = Modifier.height(5.dp))

        if(search.isNotEmpty()){
            viewModel.searchData(search)
        }else{
            viewModel.getData()
        }
            val userDetails by viewModel.userDetails.observeAsState(initial = emptyList())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
            ) {
                items(items = userDetails) {
                    it.cordinates?.let { it1 ->
                        UserOption(ItemName = it.name.toString(), cordinates = it1, id = it.id)
                    }
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

// Alert dailog that help in giving name and renaming the data or a new location
@Composable
fun addAlertDailod(
    openDialog: MutableState<Boolean>,
    id: String? = null
) {
    val nunito_sans = Font(R.font.nunito_sans)
    val nunito_bold = Font(R.font.nunito_sans_bold)
    var name by remember { mutableStateOf("") }
    var openDialog = openDialog
    val context = LocalContext.current
    val database = Firebase.firestore
    val currentUid = FirebaseAuth.getInstance().currentUser!!.uid


    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    if(name.length >= 1) {
                        if (id.isNullOrEmpty()) {
                            context.startActivity(Intent(context, MapsActivity::class.java)
                                .putExtra("name",name))
                        }else {
                            database
                                .collection("found/locations/$currentUid")
                                .document(id)
                                .update("name",name)
                        }
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
                    Text(
                        text =
                        if (id.isNullOrEmpty()) {
                            "Next"
                        } else {
                            "Save"
                        },
                        fontFamily = FontFamily(nunito_bold)
                    )
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
                Text(
                    text =
                    if (id.isNullOrEmpty()) {
                        "Name the place"
                    } else {
                        "Re-name the place"
                           },
                    fontFamily = FontFamily(nunito_bold),
                    fontSize = 18.sp,
                )
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

// Alert dailog that confirms the deletion of a data
@Composable
fun deleteAlertDailog(
    openDailog: MutableState<Boolean>,
    id: String?
) {
    val nunito_sans = Font(R.font.nunito_sans)
    var openDialog = openDailog
    val database = Firebase.firestore
    val currentUid = FirebaseAuth.getInstance().currentUser!!.uid

    if (openDailog.value) {
        AlertDialog(
            onDismissRequest = { openDailog.value = false },
            title = {
                Text(
                    text = "Delete",
                    fontFamily = FontFamily(nunito_sans)
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this location.",
                    fontFamily = FontFamily(nunito_sans)
                )
            },
            confirmButton = {
                Button(onClick = {
                    database
                        .collection("found/locations/$currentUid")
                        .document(id.toString())
                        .delete()
                    openDialog.value = false
                },
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(dimred),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delete",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "", modifier = Modifier.size(18.dp), tint = secondary10)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cancel", color = secondary10, fontSize = 15.sp)
                }
            }
        )
    }
}

// Sample or model of item used in lazycolumn
@Composable
fun UserOption(
    ItemName: String,
    cordinates : GeoPoint,
    id: String?
) {
    val openAddDialog = remember { mutableStateOf(false)  }
    val openDeleteDailog = remember{ mutableStateOf(false)}
    addAlertDailod(openDialog = openAddDialog, id= id)
    deleteAlertDailog(openDailog = openDeleteDailog, id = id)

    var expanded by remember { mutableStateOf(false)}
    val nunito = Font(R.font.nunito_sans)
    val context = LocalContext.current
    val mapInent: Intent = Uri.parse("google.navigation:q=${cordinates.latitude},${cordinates.longitude}")
        .let { location ->
            Intent(Intent.ACTION_VIEW, location)
        }
    Box(
        modifier = Modifier
            .padding(vertical = 1.dp, horizontal = 10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20))
            .background(secondary100)
            .clickable {
                context.startActivity(mapInent)
            },
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    val red = Color(0xFFea4335)
                    val blue = Color(0xFF4285f4)
                    val green = Color(0xFF34a853)
                    val yellow = Color(0xFFfbbc05)
                    val colors = arrayOf(red, blue, green, yellow)
                    val color = colors.random()
                    Icon(
                        painter = painterResource(id = R.drawable.back_circle),
                        contentDescription = "",
                        tint = color,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    val gps = painterResource(id = R.drawable.gps)
                    Icon(
                        painter = gps,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
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
            IconButton(onClick = { expanded = true }) {
                Image(
                    painter = painterResource(id = R.drawable.options_icon),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(secondary90),
                    modifier = Modifier.weight(0.1f),
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                TextButton(
                    onClick = {
                        expanded = false
                        openAddDialog.value = true
                    },
                    modifier = Modifier.width(116.dp),

                ) {
                    Text(
                        text = "Edit name",
                        fontFamily = FontFamily(nunito),
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                TextButton(
                    onClick = {
                        expanded = false
                        context.startActivity(Intent(context, MapsActivity::class.java)
                            .putExtra("id",id)
                            .putExtra("latitude",cordinates.latitude.toString())
                            .putExtra("longitude",cordinates.longitude.toString())
                        )
                    }
                ) {
                    Text(
                        text = "Edit location",
                        fontFamily = FontFamily(nunito),
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
                Divider(thickness = 0.5.dp)
                TextButton(
                    onClick = {
                        expanded = false
                        openDeleteDailog.value = true
                    },
                    modifier = Modifier.width(116.dp),

                ) {
                    Text(
                        text = "Delete",
                        fontFamily = FontFamily(nunito),
                        fontSize = 15.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            }
//        }


    }
    }
}
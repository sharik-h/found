package com.example.found.SearchPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.found.R



@Composable
fun Searchpage() {
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

        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.size(90.dp)) {
            Image(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(5.dp) )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prec() {
    Searchpage()
}
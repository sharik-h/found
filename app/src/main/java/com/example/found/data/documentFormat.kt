package com.example.found.data

import com.google.firebase.firestore.GeoPoint

data class documentFormat(
    var name: String? = null ,
    var cordinates: GeoPoint? = null
)
package com.example.found.Maps


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaParser.SeekPoint.START
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.FirebaseFirestoreKtxRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.*
import kotlin.reflect.KProperty


class MapsActivity: ComponentActivity() {

    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var currentLocation: Location



    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        val IsFineLocallowed = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val IsCoarseLocAllowed = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val granted = PackageManager.PERMISSION_GRANTED

        if (IsFineLocallowed != granted && IsCoarseLocAllowed != granted)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        val task = fusedLocationProvider.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                setContent {
                    SpecimenMap()
                }
            }
        }
    }

    @Composable
    fun SpecimenMap() {
        val currentUid = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.firestore
        val name = intent.getStringExtra("name")

        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }
        var markerState = rememberMarkerDragState()
        var cordinates  by remember { mutableStateOf( LatLng(currentLocation.latitude,currentLocation.longitude) )}
        var cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(cordinates,15f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            Marker(
                position = cordinates,
                title = "Save this Spot",
                snippet = "Click this box to save this location",
                draggable = true,
                markerDragState = markerState,
                onInfoWindowClick = {
                    latitude = it.position.latitude.toString()
                    longitude = it.position.longitude.toString()

                    if (name != null){
                        Log.d("name",name)

                        val location = hashMapOf(
                            "name" to name,
                            "latitude" to latitude,
                            "longitude" to longitude
                        )
                        database
                            .collection("found/locations/$currentUid")
                            .add(location)
                            .addOnSuccessListener {
                                Log.d("dataStatus","data added successfully")
                            }.addOnFailureListener{
                                Log.d("dataStatus","Something went wrong",it)
                            }
                        finish()
                    }

                }
            )
        }
    }
}
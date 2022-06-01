package com.example.found.Maps


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.compose.rememberMarkerDragState


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
        var cordinates = LatLng(currentLocation.latitude,currentLocation.longitude)
        var cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(cordinates,15f)
        }
        Box() {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition
            ) {
                Marker(
                    position = cordinates,
                    title = "you are here",
                    snippet = cordinates.latitude.toString(),
                    draggable = true
                )
            }
        }
    }
}
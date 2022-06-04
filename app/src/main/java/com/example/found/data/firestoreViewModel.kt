package com.example.found.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class firestoreViewModel: ViewModel() {

    val currentUid = FirebaseAuth.getInstance().uid
    val database = Firebase.firestore
    val userDetails: MutableLiveData<List<documentFormat>> = MutableLiveData<List<documentFormat>>()


    fun getData(): MutableList<documentFormat> {
        var documents : MutableList<documentFormat> = mutableListOf()
        database
            .collection("found/locations/$currentUid")
            .addSnapshotListener {
                querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { querySnapshot ->
                    var documents = querySnapshot.documents
                    var inLocation = ArrayList<documentFormat>()
                    documents.forEach{
                        var location = it.toObject(documentFormat::class.java)
                        location?.let {
                            inLocation.add(location)
                        }
                    }
                    userDetails.value = inLocation
                }
            }
        return documents
    }
}
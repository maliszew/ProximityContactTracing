package com.maliszew.proximitycontacttracing.models

//import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase

data class Users(
    var androidver: String,
    var name: String
) {

    @Exclude
    private lateinit var database: DatabaseReference

    @Exclude
    private fun writeNewUser(userId: String, name: String, android:String) {
        val user = Users(android, name)
        database.child("users").child(userId).setValue(user)
    }
}

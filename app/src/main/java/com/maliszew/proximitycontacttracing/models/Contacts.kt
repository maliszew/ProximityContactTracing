package com.maliszew.proximitycontacttracing.models

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

data class Contacts(
    var receiver: String, // current user or device
    var senderName: String, // title
    var senderId: String, // subtitle
    var date_time: String, // timestamp of event
    var tracingType: String, // automatic or manual
    var eventType: String // onEnter or onExit
) {

    @Exclude
    private lateinit var database: DatabaseReference

    @Exclude
    private fun writeNewContact(receiverId: String, senderName: String, senderId: String, time: String, tracingType: String, eventType: String) {
        val contact = Contacts(receiverId, senderName, senderId, time, tracingType, eventType)
        val contactValues = contact.toMap()

        val key = database.child("contacts").push().key
        if(key == null) {
            Log.w("maliszew/firebase", "Could not get a new key!")
            return
        }

        val childUpdates = hashMapOf<String, Any>(
            "/contacts/$key" to contactValues
        )

        database.updateChildren(childUpdates)
    }

    @Exclude
    fun toMap(): Map<String,  Any?> {
        return mapOf(
            "receiver" to receiver,
            "senderName" to senderName,
            "senderId" to senderId,
            "date_time" to date_time,
            "tracingType" to tracingType,
            "eventType" to eventType
        )
    }
}
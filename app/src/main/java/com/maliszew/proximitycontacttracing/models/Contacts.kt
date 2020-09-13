package com.maliszew.proximitycontacttracing.models

import android.util.Log
import com.estimote.proximity_sdk.api.ProximityZoneContext
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

data class Contacts(
    var receiver: String, // current user or device
    var senderName: String, // title
    var senderId: String, // subtitle
    var date_time: String, // timestamp of event
    var tracingType: String, // automatic or manual
    var eventType: String // onEnter or onExit
) {

    private var database: DatabaseReference = Firebase.database.reference

    @Exclude
    private fun writeNewContact(contact: Contacts) {
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
        Log.d("maliszew/Contacts", "writing contact into db: $childUpdates")
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

    companion object {
        fun logContacts(contexts: Set<ProximityZoneContext>, eventType: String, tracingType: String) {
            for(context in contexts) {
                val contact: Contacts = Contacts(
                    "3", // TODO replace with real device ID
                    context.attachments["maliszew-contact-tracing/title"]  ?: "unknown!!!",
                    context.deviceId,
                    Calendar.getInstance().timeInMillis.toString(), // LocalDateTime.now().toString() -> requires API level 26
                    tracingType,
                    eventType
                )
                contact.writeNewContact(contact)
                // Log.d("maliszew/Contacts", "building Contacts object: $contact")
            }
        }

        fun logManualContacts(beacon: String, eventType: String) {
            val contact: Contacts = Contacts(
                "3",
                beacon,
                "",
                Calendar.getInstance().timeInMillis.toString(),
                "manual",
                eventType
            )
            contact.writeNewContact(contact)
        }
    }

}
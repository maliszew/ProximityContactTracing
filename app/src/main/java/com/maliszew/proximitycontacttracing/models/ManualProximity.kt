package com.maliszew.proximitycontacttracing.models

class ManualProximity {
    var toggle1: Boolean = false
    var toggle2: Boolean = false
    var toggle3: Boolean = false


    fun startManualTracking(beacon: String) {

        Contacts.logManualContacts(beacon, "onEnter")
    }

    fun stopManualTracking(beacon: String) {

        Contacts.logManualContacts(beacon, "onExit")
    }
}
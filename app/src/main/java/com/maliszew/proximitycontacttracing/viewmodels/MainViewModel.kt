package com.maliszew.proximitycontacttracing.viewmodels

import android.util.Log
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.maliszew.proximitycontacttracing.models.Contacts

class MainViewModel : ViewModel() {
    private val myObserver: Observer = Observer()

    fun getObserver(): Observer {
        return myObserver
    }

    inner class Observer() : BaseObservable() {

        @Bindable
        var switch1: Boolean? = null

        fun afterSwitchClicked(input: Boolean, beacon: String) {
            Log.d("maliszew/ViewModel", "switch for $beacon changed to $input")

            if(input) {
                Contacts.logManualContacts(beacon, "onEnter")
            }
            else {
                Contacts.logManualContacts(beacon, "onExit")
            }
        }


    }


}
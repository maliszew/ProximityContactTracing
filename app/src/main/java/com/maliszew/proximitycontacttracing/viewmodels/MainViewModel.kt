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
import com.maliszew.proximitycontacttracing.models.ProximityContent
import com.maliszew.proximitycontacttracing.BR

class MainViewModel : ViewModel() {
    private val myObserver: Observer = Observer()

    fun getObserver(): Observer {
        return myObserver
    }

    inner class Observer() : BaseObservable() {

        @Bindable
        var switch1: Boolean? = null

        @Bindable
        private var nearbyContent: List<ProximityContent> = ArrayList()

        fun afterSwitchClicked(input: Boolean, beacon: String) {
            Log.d("maliszew/ViewModel", "switch for $beacon changed to $input")

            if(input) {
                Contacts.logManualContacts(beacon, "onEnter")
            }
            else {
                Contacts.logManualContacts(beacon, "onExit")
            }
        }

        fun setNearbyContent(nearbyContents: List<ProximityContent>) {
            this.nearbyContent = nearbyContents
            for(content in nearbyContent) {
                Log.d("maliszew/ViewModel", "current beacons nearby: ${content.title} ${content.id}")
            }
        }

        private fun updateNearbyContent() {
            notifyPropertyChanged(BR.nearbyContent)
        }


    }


}
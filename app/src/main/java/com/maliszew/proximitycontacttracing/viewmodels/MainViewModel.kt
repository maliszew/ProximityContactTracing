package com.maliszew.proximitycontacttracing.viewmodels

import android.util.Log
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class MainViewModel : ViewModel() {
    private val myObserver: Observer = Observer()

    fun getObserver(): Observer {
        return myObserver
    }

    inner class Observer() : BaseObservable() {

        @Bindable
        var switch1: Boolean? = null

        fun afterSwitchClicked(input: Boolean) {
                Log.d("maliszew/ViewModel", "switch changed to $input")
        }

        /*
        @Bindable
        var availabilityState = MutableLiveData<Boolean>(false)

        val afterSwitchChanged: LiveData<Boolean> = Transformations.map(availabilityState) {
            if (it) {

            }
            Log.d("maliszew/ViewModel", "switch changed to $availabilityState")
        }
        */
    }


}
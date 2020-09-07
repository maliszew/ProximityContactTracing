package com.maliszew.proximitycontacttracing.viewmodels

import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class MainViewModel : ViewModel() {
    private val myObserver: Observer = Observer()

    fun getObserver(): Observer {
        return myObserver
    }

    inner class Observer() : BaseObservable() {

    }
}
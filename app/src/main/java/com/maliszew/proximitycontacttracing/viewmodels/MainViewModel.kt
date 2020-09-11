package com.maliszew.proximitycontacttracing.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maliszew.proximitycontacttracing.models.Contacts
import com.maliszew.proximitycontacttracing.models.ProximityContent
import com.maliszew.proximitycontacttracing.BR
import com.maliszew.proximitycontacttracing.R
import com.maliszew.proximitycontacttracing.views.RecyclerItem
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val myObserver: Observer = Observer()

    fun getObserver(): Observer {
        return myObserver
    }

    inner class Observer() : BaseObservable() {

        @Bindable
        var switch1: Boolean? = null

        @Bindable
        var beaconnn: ProximityContent? = null

        private var nearbyContent: List<ProximityContent> = ArrayList()

        @Bindable
        val nearbyContentData = MutableLiveData<List<RecyclerItem>>()

        private fun loadData() {
            nearbyContentData.value = nearbyContent.map {
                it.toRecyclerItem()
            }
            Log.d("maliszew/ViewModel", "nearby DATA is ${nearbyContentData} but its VALUE is ${nearbyContentData.value}")
            notifyPropertyChanged(BR.nearbyContentData)
            notifyPropertyChanged(BR.beacon)
        }

        init {
            viewModelScope.launch {
                loadData()
                Log.d("maliszew/ViewModel", "load DATA")
            }
            Log.d("maliszew/ViewModel", "load DATA outside")
        }

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
            //this.nearbyContentData = nearbyContents
            for(content in nearbyContent) {
                Log.d("maliszew/ViewModel", "current beacons nearby: ${content.title} ; but DATA is ${nearbyContentData.value}")
            }
            loadData()
        }

        private fun ProximityContent.toRecyclerItem() = RecyclerItem(
            data = this,
            variableId = BR.beacon,
            layoutId = R.layout.recycler_rows
        )

        //private fun updateNearbyContent() {
        //    notifyPropertyChanged(BR.nearbyContent)
        //}


    }


}
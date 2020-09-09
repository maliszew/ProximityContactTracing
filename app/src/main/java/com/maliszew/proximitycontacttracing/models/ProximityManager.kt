package com.maliszew.proximitycontacttracing.models

import android.content.Context
import android.util.Log
import com.estimote.proximity_sdk.api.*
import com.maliszew.proximitycontacttracing.MainActivity
import com.maliszew.proximitycontacttracing.viewmodels.MainViewModel

class ProximityManager(private val context: Context) {
    private var previousContexts: Set<ProximityZoneContext> = emptySet()
    private val cloudCredentials =  EstimoteCloudCredentials("proximity-contact-tracing--3im", "8e3263451f63f62b33bebf7f077b04ae")
    private var proximityObserverHandler: ProximityObserver.Handler? = null

    fun start() {
        Log.d("maliszew/Scanner", "Starting beacon scanner")

        val proximityObserver = ProximityObserverBuilder(context, cloudCredentials)
            .withTelemetryReportingDisabled()
            .withEstimoteSecureMonitoringDisabled()
            .onError { throwable ->
                Log.e("maliszew/Scanner", "Proximity observer error: $throwable")
            }
            .withBalancedPowerMode()
            .build()

        val zone = ProximityZoneBuilder()
            .forTag("maliszew-contact-tracing")
            .inNearRange()
            .also {
                Log.d("maliszew/Scanner", "Inside zone builder: $it")
            }
            .onEnter {
                Log.d("maliszew/Scanner", "inside onEnter... size = $context and ${it.deviceId} and ${it.attachments["maliszew-contact-tracing/title"]  ?: "unknown!!"}")

            }
            .onExit {
                Log.d("maliszew/Scanner", "inside onExit... context: $context and ${it.deviceId} and ${it.attachments["maliszew-contact-tracing/title"]  ?: "unknown!!!"}")

            }
            .onContextChange { currentContexts: Set<ProximityZoneContext> ->
                val nearbyContent = ArrayList<ProximityContent>(currentContexts.size)
                // Log.d("maliszew/Scanner",  "\n\n previous context: $previousContexts \n current context: $currentContexts")

                compareContexts(previousContexts, currentContexts)

                //var i: Int = 0
                for (context in currentContexts) {
                    //Log.d("maliszew/Scanner", "inside iteration $i of onContextChange... context: ${context.deviceId} and ${context.attachments["maliszew-contact-tracing/title"]  ?: "unknown!!!"}")
                    //i++
                    nearbyContent.add(ProximityContent((context.attachments["maliszew-contact-tracing/title"]  ?: "unknown!!!"), context.deviceId))
                }
                previousContexts = currentContexts
                (context as MainActivity).viewModel.getObserver().setNearbyContent(nearbyContent)
            }
            .build()

        proximityObserverHandler = proximityObserver.startObserving(zone)
        Log.d("maliszew/Scanner", "start observing a zone...")
    }

    fun stop() {
        proximityObserverHandler?.stop()
        Log.d("maliszew/Scanner", "observation has been stopped!")
    }

    private fun compareContexts(previousSet: Set<ProximityZoneContext>, currentSet: Set<ProximityZoneContext>) {
        val newContexts: Set<ProximityZoneContext>
        val deletedContexts: Set<ProximityZoneContext>

        if(previousSet.isEmpty()) {
            newContexts = currentSet
            deletedContexts = emptySet()
        }
        else {
            newContexts = currentSet subtract previousSet
            deletedContexts = previousSet subtract currentSet
        }

        Contacts.logContacts(newContexts, "onEnter", "automatic")
        Contacts.logContacts(deletedContexts, "onExit", "automatic")

        //TODO remove both loops as not needed for anything apart from logging
        for(context in newContexts) {
            Log.d("maliszew/Scanner", "new Enter... context: $context and ${context.deviceId}")
        }

        for(context in deletedContexts) {
            Log.d("maliszew/Scanner", "new Exit... context: $context and ${context.deviceId}")
        }
    }



}
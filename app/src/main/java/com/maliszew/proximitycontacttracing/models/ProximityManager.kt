package com.maliszew.proximitycontacttracing.models

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.estimote.proximity_sdk.api.*
import com.maliszew.proximitycontacttracing.MainActivity
import com.maliszew.proximitycontacttracing.R

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
                    var title = (context.attachments["maliszew-contact-tracing/title"] ?: "unknown!!!")
                    nearbyContent.add(
                        ProximityContent(
                            title,
                            context.deviceId,
                            getEstimoteColor(title)
                        )
                    )
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

    internal fun getEstimoteColor(colorName: String): Int {
        return when (colorName) {
            "ice" -> Color.rgb(109, 170, 199)

            "blueberry" -> Color.rgb(36, 24, 93)

            "candy", "candy1" -> Color.rgb(219, 122, 167)

            "mint" -> Color.rgb(155, 186, 160)

            "beetroot", "beetroot1" -> Color.rgb(84, 0, 61)

            "lemon", "lemon1" -> Color.rgb(195, 192, 16)

            else -> R.color.design_default_color_background
        }
    }


}
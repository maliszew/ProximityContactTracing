package com.maliszew.proximitycontacttracing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.maliszew.proximitycontacttracing.models.ProximityManager
import com.maliszew.proximitycontacttracing.views.MainFragment

class MainActivity : AppCompatActivity() {
    private var proximityContentManager: ProximityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        RequirementsWizardFactory
            .createEstimoteRequirementsWizard()
            .fulfillRequirements(this,
                {
                    Log.d("maliszew/MainActivity", "Requirements fulfilled")
                    startProximityContentManager()
                },
                {requirements ->
                    Log.e("maliszew/MainActivity", "Requirements missing: " + requirements)
                },
                { throwable ->
                    Log.e("maliszew/MainActivity", "Requirements error: " + throwable)
                }
            )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    private fun startProximityContentManager() {
        proximityContentManager = ProximityManager(this)
        Log.d("maliszew/MainActivity", "Starting content manager from main...")
        proximityContentManager?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        proximityContentManager?.stop()
    }
}
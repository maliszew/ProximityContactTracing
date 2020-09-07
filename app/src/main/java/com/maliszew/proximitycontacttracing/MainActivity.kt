package com.maliszew.proximitycontacttracing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.maliszew.proximitycontacttracing.models.ProximityManager
import com.maliszew.proximitycontacttracing.views.MainFragment
import com.maliszew.proximitycontacttracing.databinding.*
import com.maliszew.proximitycontacttracing.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private var proximityContentManager: ProximityManager? = null
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //val activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        val mainActivityBinding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get<MainViewModel>(
            MainViewModel::class.java)
        mainActivityBinding.viewModel = viewModel.getObserver()
        mainActivityBinding.executePendingBindings()

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
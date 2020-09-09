package com.maliszew.proximitycontacttracing.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.maliszew.proximitycontacttracing.R
import com.maliszew.proximitycontacttracing.databinding.MainFragmentBinding
import com.maliszew.proximitycontacttracing.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(
            inflater, //R.layout.main_fragment,
            container,
            false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = viewModel
            }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get<MainViewModel>(MainViewModel::class.java)
        //viewModel = MainViewModel by viewModels()
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val mainFragmentBinding: MainFragmentBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_fragment)
        mainFragmentBinding.viewModel = viewModel.getObserver()
        mainFragmentBinding.executePendingBindings()

        switch01.setOnCheckedChangeListener { viewModel, isChecked ->
            Log.d("maliszew/Fragment", "??????? $isChecked")
        }
    }

}
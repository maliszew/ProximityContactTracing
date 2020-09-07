package com.maliszew.proximitycontacttracing.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
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
    }

}
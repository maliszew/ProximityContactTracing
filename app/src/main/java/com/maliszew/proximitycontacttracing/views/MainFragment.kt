package com.maliszew.proximitycontacttracing.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maliszew.proximitycontacttracing.R
import com.maliszew.proximitycontacttracing.databinding.MainFragmentBinding
import com.maliszew.proximitycontacttracing.viewmodels.MainViewModel

class MainFragment(private var viewModel: MainViewModel) : Fragment() {

    companion object {
        fun newInstance() = MainFragment(viewModel = MainViewModel())
    }

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
                viewModelFragment  = viewModel.Observer()
            }.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val mainFragmentBinding: MainFragmentBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_fragment)
        mainFragmentBinding.viewModelFragment = viewModel.getObserver()
        mainFragmentBinding.executePendingBindings()
    }

}
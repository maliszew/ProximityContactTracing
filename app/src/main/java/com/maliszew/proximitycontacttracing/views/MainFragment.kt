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
import com.maliszew.proximitycontacttracing.databinding.RecyclerRowsBinding
import com.maliszew.proximitycontacttracing.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

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

        /*RecyclerRowsBinding.inflate(
            inflater,
            container,
            false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModellll = viewModel
            }.root

         */
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get<MainViewModel>(MainViewModel::class.java)
        //viewModel = MainViewModel by viewModels()
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val mainFragmentBinding: MainFragmentBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_fragment)
        // DataBindingUtil.inflate<MainFragmentBinding>(LayoutInflater.from(context), R.layout.main_fragment, null, false)
        mainFragmentBinding.viewModelFragment = viewModel.getObserver()
        mainFragmentBinding.executePendingBindings()

        //val recyclerRowsBinding: RecyclerRowsBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.recycler_rows)
        // DataBindingUtil.inflate<RecyclerRowsBinding>(LayoutInflater.from(context), R.layout.recycler_rows, null, false)
        //recyclerRowsBinding.viewModelRecycler = viewModel.getObserver()
        //recyclerRowsBinding.executePendingBindings()

        //TODO delete this
        switch01.setOnCheckedChangeListener { viewModel, isChecked ->
            Log.d("maliszew/Fragment", "??????? $isChecked")
        }
    }

}
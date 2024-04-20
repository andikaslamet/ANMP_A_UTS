package com.example.a160421085_uts_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a160421085_uts_anmp.R
import com.example.a160421085_uts_anmp.databinding.DataListItemBinding
import com.example.a160421085_uts_anmp.databinding.FragmentDataListBinding
import com.example.a160421085_uts_anmp.model.Data
import com.example.a160421085_uts_anmp.viewmodel.ListViewModel
import java.io.LineNumberReader

class DataListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val dataListAdapter = DataListAdapter(arrayListOf())
    private lateinit var binding: FragmentDataListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDataListBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = dataListAdapter
        observeViewModel()
    }
    fun observeViewModel(){
        viewModel.DataLD.observe(viewLifecycleOwner, Observer {
            dataListAdapter.updateDataList(it)
        })
        viewModel.LoadingErrorLD.observe(viewLifecycleOwner, Observer {
            if(it==true){
                binding.txtloading.visibility = View.VISIBLE
            }else{
                binding.txtloading.visibility = View.GONE
            }
        })
        viewModel.LoadingLD.observe(viewLifecycleOwner, Observer {
            if(it==true){
                binding.recView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.recView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}
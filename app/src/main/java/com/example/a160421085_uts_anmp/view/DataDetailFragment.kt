package com.example.a160421085_uts_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.a160421085_uts_anmp.databinding.FragmentDataDetailBinding
import com.squareup.picasso.Picasso

class DataDetailFragment : Fragment() {
    private lateinit var binding:FragmentDataDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDataDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val datasID = DataDetailFragmentArgs.fromBundle(requireArguments()).ID
            binding.txtdetailID.text = datasID
            val datasName = DataDetailFragmentArgs.fromBundle(requireArguments()).NAME
            binding.txtdetailNama.text = datasName
            val datasGenre = DataDetailFragmentArgs.fromBundle(requireArguments()).GENRE
            binding.txtdetailgenre.text = datasGenre
            val datasPlat = DataDetailFragmentArgs.fromBundle(requireArguments()).PLATFORMS
            binding.txtdetailplatform.text = datasPlat
            val datasimg = DataDetailFragmentArgs.fromBundle(requireArguments()).IMAGE
            Picasso.get().load(datasimg).into(binding.imgdetailimage)
        }
    }
}
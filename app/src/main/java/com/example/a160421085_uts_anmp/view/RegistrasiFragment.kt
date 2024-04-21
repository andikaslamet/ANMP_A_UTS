package com.example.a160421085_uts_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a160421085_uts_anmp.R
import com.example.a160421085_uts_anmp.databinding.FragmentLoginBinding
import com.example.a160421085_uts_anmp.databinding.FragmentRegistrasiBinding
import com.example.a160421085_uts_anmp.viewmodel.userviewmodel

class RegistrasiFragment : Fragment() {
    private lateinit var binding:FragmentRegistrasiBinding
    private lateinit var viewmodel: userviewmodel
    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrasiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(userviewmodel::class.java)

        binding.btnregistrasi.setOnClickListener {
            try {
                val username = binding.txtregisusername.text.toString()
                val firstName = binding.txtregisnamadepan.text.toString()
                val lastName = binding.txtregisnamabelakang.text.toString()
                val email = binding.txtregisemail.text.toString()
                val password = binding.txtregispw.text.toString()

                if (username.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    viewmodel.registerUser(username, firstName, lastName, email, password)

                    viewmodel.registrationResult.observe(viewLifecycleOwner, Observer { result ->
                        if (result) {
                            Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                            val action = RegistrasiFragmentDirections.actionloginfragment()
                            Navigation.findNavController(requireView()).navigate(action)
                        } else {
                            Toast.makeText(requireContext(), "Registrasi gagal", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    // Tampilkan pesan kesalahan jika ada field yang kosong
                    Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan jika terjadi
                Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

    }


}
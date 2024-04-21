package com.example.a160421085_uts_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a160421085_uts_anmp.R
import com.example.a160421085_uts_anmp.databinding.FragmentDataListBinding
import com.example.a160421085_uts_anmp.databinding.FragmentLoginBinding
import com.example.a160421085_uts_anmp.viewmodel.userviewmodel
import java.lang.Exception

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewmodel: userviewmodel // Gunakan nama class viewmodel yang benar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi viewmodel di sini sebelum menggunakannya
        viewmodel = ViewModelProvider(this).get(userviewmodel::class.java)

        binding.btnlogin.setOnClickListener {
            try {
                val username = binding.txtusername.text.toString()
                val password = binding.txtpassword.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewmodel.cekUser(username, password)
                    viewmodel.usernameLD.observe(viewLifecycleOwner, Observer { userList ->
                        if (userList != null && userList.isNotEmpty()) {
                            // Pengguna ditemukan, navigasi ke tujuan yang sesuai
                            Toast.makeText(requireContext(), "berhasil", Toast.LENGTH_SHORT).show()
                            val action = LoginFragmentDirections.actiondatalist()
                            Navigation.findNavController(requireView()).navigate(action)
                        } else {
                            // Jika pengguna tidak ditemukan, tampilkan pesan kesalahan
                            Toast.makeText(requireContext(), "gagal login", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    // Jika username atau password kosong, tampilkan pesan kesalahan
                    Toast.makeText(requireContext(), "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan jika terjadi
                Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnregist.setOnClickListener()
        {
            val action = LoginFragmentDirections.actionregistrasi()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewmodel.clearUser()
    }
}

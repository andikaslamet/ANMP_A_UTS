package com.example.a160421085_uts_anmp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160421085_uts_anmp.model.Data
import com.example.a160421085_uts_anmp.model.user
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URLEncoder
import java.util.Queue

class userviewmodel(application: Application):AndroidViewModel (application){
    val usernameLD = MutableLiveData<ArrayList<user>?>()
    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()

    val TAG = "volleyTag"
    private var queue: RequestQueue ? = null

    fun cekUser(username:String,password:String)
    {
        queue = Volley.newRequestQueue(getApplication())
        val encodedUsername = URLEncoder.encode(username, "UTF-8")
        val encodedPassword = URLEncoder.encode(password, "UTF-8")
        val url = "http://10.0.2.2/DataAnmp/login.php?username=$encodedUsername&password=$encodedPassword"
        val stringRequest = StringRequest(
            Request.Method.GET,url,
            {

                val sType = object : TypeToken<List<user>>() {}.type
                val result = Gson().fromJson<List<user>>(it,sType)
                usernameLD.value = result as ArrayList<user>

            },
            {
                Log.d("showVolley",it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun registerUser(username: String, namadepan: String, namabelakang: String, email: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val encodedUsername = URLEncoder.encode(username, "UTF-8")
        val encodedFirstName = URLEncoder.encode(namadepan, "UTF-8")
        val encodedLastName = URLEncoder.encode(namabelakang, "UTF-8")
        val encodedEmail = URLEncoder.encode(email, "UTF-8")
        val encodedPassword = URLEncoder.encode(password, "UTF-8")
        val url = "http://10.0.2.2/DataAnmp/registrasi.php?username=$encodedUsername&first_name=$encodedFirstName&last_name=$encodedLastName&email=$encodedEmail&password=$encodedPassword"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    Log.d("showVolley", "Registration response: $response")
                    // Perbarui registrationResult dengan true jika registrasi berhasil
                    registrationResult.value = true
                } catch (e: Exception) {
                    Log.d("showVolley", "Error parsing registration response: ${e.message}")
                }
            },
            { error ->
                Log.d("showVolley", "Error registering user: ${error.message}")

                Toast.makeText(getApplication(), "Network error", Toast.LENGTH_SHORT).show()
                // Perbarui registrationResult dengan false jika terjadi error
                registrationResult.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun clearUser()
    {
        usernameLD.value = null
    }
}
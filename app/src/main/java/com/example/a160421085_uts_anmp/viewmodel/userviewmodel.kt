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
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.Queue

class userviewmodel(application: Application):AndroidViewModel (application){
    val usernameLD = MutableLiveData<ArrayList<user>?>()
    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()

    val TAG = "volleyTag"
    private var queue: RequestQueue ? = null

    fun cekUser(username: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val encodedUsername = URLEncoder.encode(username, "UTF-8")
        val encodedPassword = URLEncoder.encode(password, "UTF-8")

        val url = "http://10.0.2.2/DataAnmp/login.php?username=$encodedUsername&password=$encodedPassword"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (status == "success") {
                        val userObject = jsonObject.getJSONObject("user")
                        // Ubah objek JSON pengguna menjadi objek model user
                        val user = user(
                            userObject.getString("username"),
                            userObject.getString("namadepan"),
                            userObject.getString("namabelakang"),
                            userObject.getString("email"),
                            userObject.getString("password")
                        )
                        // Atur nilai LiveData usernameLD dengan objek user
                        usernameLD.value = arrayListOf(user)
                    } else {
                        // Tampilkan pesan kesalahan jika pengguna tidak ditemukan
                        Log.d("showVolley", message)
                    }
                } catch (e: JSONException) {
                    // Tangani kesalahan parsing JSON
                    Log.d("showVolley", "Error parsing JSON response: ${e.message}")
                }
            },
            { error ->
                Log.d("showVolley", error.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun registerUser(username: String, namadepan: String, namabelakang: String, email: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())

        val url = "http://10.0.2.2/DataAnmp/registrasi.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
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
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["namadepan"] = namadepan
                params["namabelakang"] = namabelakang
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }


    fun clearUser()
    {
        usernameLD.value = null
    }
}
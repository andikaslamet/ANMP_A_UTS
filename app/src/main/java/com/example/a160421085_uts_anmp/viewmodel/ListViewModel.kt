package com.example.a160421085_uts_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160421085_uts_anmp.model.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(application: Application): AndroidViewModel(application) {
    val DataLD = MutableLiveData<List<Data>>()
    val LoadingErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    private val TAG = "volleyTag"
    private var queue:RequestQueue ?= null
    fun refresh() {
        LoadingErrorLD.value = false
        LoadingLD.value = true
        queue = Volley.newRequestQueue(getApplication())

        val url = "http://10.0.2.2/DataAnmp/Data.json"
        val stringRequest = StringRequest(Request.Method.GET,url,
            {
                val sType = object : TypeToken<List<Data>>() {}.type
                val result = Gson().fromJson<List<Data>>(it,sType)
                DataLD.value = result
                LoadingLD.value = false
                Log.d("showVolley",it)
            },
            {
                LoadingLD.value = false
                LoadingErrorLD.value = true
                Log.d("showVolley",it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
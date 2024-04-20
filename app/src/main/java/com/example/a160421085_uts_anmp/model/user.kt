package com.example.a160421085_uts_anmp.model

import com.google.gson.annotations.SerializedName

data class user(
    @SerializedName("username")
    val username:String?,
    @SerializedName("namadepan")
    val namaDepan:String?,
    @SerializedName("namabelakang")
    val namaBelakang:String?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("password")
    val password:String?
)

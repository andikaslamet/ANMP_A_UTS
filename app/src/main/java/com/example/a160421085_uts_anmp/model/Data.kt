package com.example.a160421085_uts_anmp.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id:String?,
    @SerializedName("name")
    val title:String?,
    @SerializedName("genre")
    val genre:String?,
    @SerializedName("platforms")
    val platforms:String?,
    @SerializedName("images")
    val image:String?
)

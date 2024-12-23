package com.example.sumitwork.network

import com.google.gson.annotations.SerializedName


data class ResponseModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("image")
    var image: String
)
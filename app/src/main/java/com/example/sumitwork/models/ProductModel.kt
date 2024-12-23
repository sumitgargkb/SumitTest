package com.example.sumitwork.models

import com.google.gson.annotations.SerializedName

data class ProductModel(
    var id: Int,
    var name: String,
    var price: Int,
    var image: String
)
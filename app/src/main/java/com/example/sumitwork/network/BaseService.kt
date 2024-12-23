package com.example.sumitwork.network

import com.example.sumitwork.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET


interface BaseService {
    @GET(AppConstants.products)
    suspend fun requestProducts(
    ): Response<List<ResponseModel>?>
}
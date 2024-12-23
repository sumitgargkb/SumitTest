package com.example.sumitwork.repository

import com.example.sumitwork.network.Envelope
import com.example.sumitwork.network.ResponseModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getProducts(): Flow<Envelope<List<ResponseModel>>>
}
package com.example.sumitwork.repository

import com.example.sumitwork.network.BaseService
import com.example.sumitwork.network.Envelope
import com.example.sumitwork.network.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: BaseService,
) : Repository{

    override fun getProducts(): Flow<Envelope<List<ResponseModel>>> = flow {
        emit(Envelope.loading())
        val response = remote.requestProducts()
        if (response.isSuccessful && response.body() != null) {
            emit(Envelope.success(response.body()!!))
        } else {
            emit(
                Envelope.Error("There is some error")
            )
        }
    }.catch { e ->
        emit(Envelope.Error("There is exception"))
        e.printStackTrace()
    }

}

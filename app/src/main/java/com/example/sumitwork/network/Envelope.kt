package com.example.sumitwork.network


sealed class Envelope<T> {

    class Loading<T> : Envelope<T>()
    data class Success<T>(val data: T) : Envelope<T>()
    data class Error<T>(val error: String) : Envelope<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> error(error: String) = Error<T>(error)
    }

}
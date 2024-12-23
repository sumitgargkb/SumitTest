package com.example.sumitwork.screens

import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.network.ResponseModel
import com.example.sumitwork.utils.ViewEvent
import com.example.sumitwork.utils.ViewSideEffect
import com.example.sumitwork.utils.ViewState


class HomeContract {
    sealed class Event : ViewEvent {
        data class OnItemSelection(val item: ProductModel) : Event()
    }

    data class State(
        val loading: Boolean = false,
        val productList: List<ProductModel> = listOf(),
        val selectedList: List<ProductModel> = listOf(),
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class LoadInitData(val route: String) : Effect()

        sealed class Navigation : Effect() {
            data object NavigateToCartList : Navigation()
        }
    }

}

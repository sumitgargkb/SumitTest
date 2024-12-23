package com.example.sumitwork.screens.cart

import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.utils.ViewEvent
import com.example.sumitwork.utils.ViewSideEffect
import com.example.sumitwork.utils.ViewState

class CartContract {
    sealed class Event : ViewEvent {

    }

    data class State(
        val loading: Boolean = false,
        val selectedList: List<ProductModel> = listOf(),
        val totalPrice: Int = 0
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            data object NavigateToBack : Navigation()
            data object NavigateToThankYou : Navigation()
        }
    }

}

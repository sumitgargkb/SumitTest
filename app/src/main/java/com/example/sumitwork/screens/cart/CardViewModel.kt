package com.example.sumitwork.screens.cart

import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.repository.Repository
import com.example.sumitwork.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>() {

    override fun initialState() = CartContract.State()

    override fun handleEvents(event: CartContract.Event) {
        when (event) {

            else -> {}
        }
    }
    
    fun setData(list: List<ProductModel>){
        setState { copy(selectedList =  list,
            totalPrice = list.sumOf { it.price }) }
    }
}



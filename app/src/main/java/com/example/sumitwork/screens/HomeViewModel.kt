package com.example.sumitwork.screens

import androidx.lifecycle.viewModelScope
import com.example.sumitwork.models.ProductModel
import com.example.sumitwork.network.Envelope
import com.example.sumitwork.repository.Repository
import com.example.sumitwork.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun initialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnItemSelection -> {
                val list = mutableListOf<ProductModel>()
                list.addAll(viewState.value.selectedList)
                list.removeIf { it.id == event.item.id }
                list.add(event.item)
                setState {
                    copy(
                        selectedList = list
                    )
                }
            }
        }
    }



     fun getProducts() {
         if(viewState.value.productList.isEmpty()) {
             repository.getProducts().map {
                 when (it) {
                     is Envelope.Loading -> {
                         setState { copy(loading = true) }
                     }

                     is Envelope.Success -> {
                         setState {
                             copy(loading = false,
                                 productList = it.data.map {
                                     ProductModel(
                                         id = it.id,
                                         name = it.name,
                                         image = it.image,
                                         price = it.price
                                     )
                                 })
                         }
                     }

                     is Envelope.Error -> {
                         setState { copy(loading = false) }
                     }
                 }
             }.launchIn(viewModelScope)
         }
    }
}

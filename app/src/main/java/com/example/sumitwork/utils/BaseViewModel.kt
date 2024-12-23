package com.example.sumitwork.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


interface ViewEvent
interface ViewState
interface ViewSideEffect

const val SIDE_EFFECTS_KEY = "effect_key"

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, SideEffect : ViewSideEffect> :
    ViewModel() {

    abstract fun initialState(): UiState
    abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy { initialState() }

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<SideEffect> = Channel()
    val effect = _effect.receiveAsFlow()



    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> SideEffect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    fun resetViewState() {
        _viewState.value = initialState
    }

    companion object {
        const val TAG = "BaseViewModel"
    }
}
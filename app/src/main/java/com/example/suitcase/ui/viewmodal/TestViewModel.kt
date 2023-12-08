package com.example.suitcase.ui.viewmodal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestViewModel:ViewModel() {
    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    fun showHideDialog(){
        _state.value = !_state.value
    }
}
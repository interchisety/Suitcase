package com.example.suitcase.ui.viewmodal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class NewListViewModal:ViewModel() {
    private val _state = MutableStateFlow(NewListState())
    val state = _state.asStateFlow()



    //events for changing user inputed value
    fun onEvent(event: NewListEvent){
        when(event){
            is NewListEvent.SetDesc -> {
                _state.update { it.copy(
                    desc = event.desc
                ) }
            }
            is NewListEvent.SetListName -> {
                _state.update { it.copy(
                    listname = event.name
                ) }
            }

            NewListEvent.UpdateListDetails -> TODO()
        }

    }
}

data class NewListState(
    val listname:String = "",
    val desc:String = "",
    val createdate:LocalDateTime = LocalDateTime.now()
)

sealed interface NewListEvent{
    data class SetListName(val name:String): NewListEvent
    data class SetDesc(val desc:String): NewListEvent
    object UpdateListDetails: NewListEvent
}
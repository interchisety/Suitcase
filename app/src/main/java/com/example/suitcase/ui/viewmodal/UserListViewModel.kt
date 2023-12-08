package com.example.suitcase.ui.viewmodal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitcase.AppContainer
import com.example.suitcase.data.room.models.UserList
import com.example.suitcase.ui.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class UserListViewModel(
    private val repository: Repository = AppContainer.repository
): ViewModel()
{
    private val _state = MutableStateFlow(UserListState())
    val state = _state.asStateFlow()

    init {
        getUserList()
    }

    private fun getUserList(){
        viewModelScope.launch {
           repository.userlists.collectLatest{userLists ->
               _state.value = _state.value.copy(
                   userlists = userLists
               )
           }
        }
    }


    fun onEvent(event: UserListEvent){

        when(event){
            is UserListEvent.DeleteList -> {
                viewModelScope.launch {
                    try {
                        // Uncomment and implement when ready
                        // dao.deleteUserList(event.userlist)
                    } catch (e: Exception) {
                        // Handle the exception, e.g., show an error message to the user
                        handleException(e)
                    }
                }
            }

            UserListEvent.SaveUserList -> {
                val title = state.value.title
                val desc = state.value.desc
                val createddate = state.value.createddate

                if (title.isBlank() || desc.isBlank()) {
                    return
                }

                val userlist = UserList(
                    title = title,
                    desc = desc,
                    createdDate = createddate
                )

                viewModelScope.launch {
                    try {
                        repository.upsertUserList(userlist)
                        // Uncomment and implement when ready
                        // dao.upsertUserList(userlist)
                    } catch (e: Exception) {
                        // Handle the exception, e.g., show an error message to the user
                        handleException(e)
                    }
                }

                _state.update { it.copy(
                    title = "",
                    desc ="",
                    createddate = LocalDateTime.now(),
                    isAddingList = false
                ) }
            }

            is UserListEvent.SetDesc -> {
                _state.update { it.copy(desc = event.desc) }
            }

            is UserListEvent.SetTitle -> {
                _state.update { it.copy(title = event.title)}
            }

            UserListEvent.HideDialog -> {
                _state.update {it.copy( isAddingList = false)}
            }

            UserListEvent.ShowDialog -> {
                _state.update {it.copy( isAddingList = true)}
            }
        }
    }

    private fun handleException(exception: Exception) {
        // Implement your error handling logic here, e.g., logging or showing an error message to the user
        Log.e("UserListViewModel", "Error: ${exception.message}", exception)
        // You can also use a LiveData to communicate the error to the UI if needed
        // _errorLiveData.value = "An error occurred: ${exception.message}"
    }
}

data class UserListState(
    val userlists: List<UserList> = emptyList(),
    val title:String = "",
    val desc:String = "",
    val createddate: LocalDateTime = LocalDateTime.now(),
    val isAddingList:Boolean = false
)

sealed interface UserListEvent{
    object ShowDialog: UserListEvent
    object HideDialog: UserListEvent
    object SaveUserList: UserListEvent
    data class SetTitle(val title:String): UserListEvent
    data class SetDesc(val desc:String): UserListEvent
    data class DeleteList(val userlist: UserList): UserListEvent
}
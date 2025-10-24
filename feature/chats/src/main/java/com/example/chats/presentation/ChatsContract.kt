package com.example.chats.presentation

import androidx.paging.PagingData
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState
import kotlinx.coroutines.flow.Flow

class ChatsContract {

    sealed class Event : ViewEvent {
        data object LoadChats : Event()
        data class SearchQueryChanged(val query: String) : Event()
        data class ChatItemClicked(val id: Int) : Event()
        data class UserItemClicked(val id:Int):Event()

        data object OnLoading : Event()
        data class OnError(val error: String) : Event()
        data object OnDataLoaded : Event()
        data object OnEmptyDataLoaded:Event()
        object Refresh : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val searchQuery: String = "",
        val chatsPagingFlow: Flow<PagingData<ChatEntity>>? =null,
        val isEmptyResults: Boolean = false,
        val error: String? = null,
        val isRefreshing: Boolean = false,
        val isUserList :Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            data class ToChat(val chatId: Int) : Navigation()
        }
    }


}
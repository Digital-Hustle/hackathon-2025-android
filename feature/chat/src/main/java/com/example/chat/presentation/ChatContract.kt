package com.example.chat.presentation

import androidx.paging.PagingData
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.core.presentation.ViewEvent
import com.example.core.presentation.ViewSideEffect
import com.example.core.presentation.ViewState
import kotlinx.coroutines.flow.Flow

class ChatContract {

    sealed class Event : ViewEvent {
//        data object LoadMessages : Event()
        data class TextInputChanged(val newValue: String) : Event()
        data object OnSendMessage : Event()

        data object OnLoading : Event()
        data class OnError(val error: String) : Event()
        data object OnDataLoaded : Event()
        data object OnEmptyDataLoaded : Event()
//        object Refresh : Event()

        object BackButtonClicked : Event()
        object ProfileClicked : Event()
    }

    data class State(
        val chatId: Int? = null,
        val userName:String? = null,
        val secondUserName:String? = null,
        val chat:ChatEntity? = null,
        val isLoading: Boolean = false,
        val inputText: String = "",
        val messagesPagingFlow: Flow<PagingData<MessageEntity>>? = null,
        val isEmptyResults: Boolean = false,
        val error: String? = null,
        val isRefreshing: Boolean = false,
    ) : ViewState

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            data object ToChats : Navigation()
            data class ToProfile(val userName:String): Navigation()
        }
    }


}
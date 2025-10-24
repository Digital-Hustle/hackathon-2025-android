package com.example.chat.presentation

import androidx.lifecycle.viewModelScope
import com.example.chat.domain.repository.ChatRepository
import com.example.chat.presentation.ChatContract.State
import com.example.core.domain.UserStorage
import com.example.core.mLog
import com.example.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val userStorage: UserStorage
) : BaseViewModel<ChatContract.Event, State, ChatContract.Effect>() {


    override fun setInitialState(): State {


        return State(
            isLoading = true,
        )
    }


    init {

    }


    override fun handleEvents(event: ChatContract.Event) {
        when (event) {
//            ChatContract.Event.LoadMessages -> TODO()
            ChatContract.Event.OnDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = false
//                    isEmptyResults = event.isEmptyResults
                )
            }

            ChatContract.Event.OnEmptyDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = true
                )
            }

            is ChatContract.Event.OnError -> setState {
                copy(
                    isLoading = false,
                    error = event.error,
                    isEmptyResults = false
                )
            }

            ChatContract.Event.OnLoading -> setState {
                copy(
                    isLoading = true,
                    error = null,
                    isEmptyResults = false
                )
            }

            ChatContract.Event.OnSendMessage -> {
                repository.sendMessage(viewState.value.inputText)
                setState { copy(inputText = "") }
            }
//            ChatContract.Event.Refresh -> TODO()
            ChatContract.Event.BackButtonClicked -> setEffect { ChatContract.Effect.Navigation.ToChats }
            ChatContract.Event.ProfileClicked -> setEffect {
                ChatContract.Effect.Navigation.ToProfile(
                    viewState.value.secondUserName!!
                )
            }

            is ChatContract.Event.TextInputChanged -> setState { copy(inputText = event.newValue) }
        }
    }

    fun setChatID(chatId: Int) {

        setState { copy(chatId = chatId) }
        setChat(chatId)

    }

    private fun setChat(chatId: Int) {

        viewModelScope.launch {
            val repo = repository.getChat(chatId)
            setState { copy(chat = repo) }
        }
        repository.setChatId(chatId)

        setMessagesFlow(chatId)
        repository.connect()

    }
    private fun setMessagesFlow(chatId: Int){
        viewModelScope.launch {
            userStorage.getUsername().collect { value ->
                if(value!=null){
                    repository.setUserName(value)

                setState { copy(userName = userName,messagesPagingFlow = repository.getMessages(chatId, value)) }
                }
            }
        }

    }

    fun closedChat(){
        repository.disconnect()

    }



}

package com.example.chats.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.chats.data.mapper.toChat
import com.example.chats.data.paging.SearchPagingSource
import com.example.chats.domain.repository.ChatsRepository
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val repository: ChatsRepository,
) : BaseViewModel<ChatsContract.Event, ChatsContract.State, ChatsContract.Effect>() {



    override fun setInitialState(): ChatsContract.State {

        return ChatsContract.State(
            isLoading = true,
//            chatsPagingFlow = Pager(
//        config = PagingConfig(pageSize = 10),
//        pagingSourceFactory = {
//            ChatDatabase.getInstance(
//                context = context
//            ).chatDao().getChatsPagingSource()
//        }
//    ).flow.cachedIn(viewModelScope)

        )
    }


    private val SearchQueryChangesFlow = MutableSharedFlow<String>()


    init {

        viewModelScope.launch {
            SearchQueryChangesFlow
                .debounce(500.milliseconds) // Задержка 500 мс
                .collect {
                    setEvent(ChatsContract.Event.LoadChats)
                }
        }
        setState { copy(chatsPagingFlow = repository.getChats()) }

//        Log.d("mLOGGAZ4",test2())
//        Log.d("mLOGGAZ4",test())


    }

    override fun handleEvents(event: ChatsContract.Event) {


        when (event) {
            is ChatsContract.Event.ChatItemClicked -> setEffect {
                ChatsContract.Effect.Navigation.ToChat(
                    event.id
                )
            }

            is ChatsContract.Event.LoadChats -> {

                setState {
                    copy(
                        chatsPagingFlow = if (viewState.value.searchQuery.isNotEmpty()) {
                            getSearchPager()
                        } else {
                            repository.getChats()
//                            getMainPager()
//                            getSearchPager()

                        }, isUserList = viewState.value.searchQuery.isNotEmpty()

                    )
                }

            }

            is ChatsContract.Event.SearchQueryChanged -> {
                setState {
                    copy(
                        searchQuery = event.query,
                        isLoading = true,
                        isEmptyResults = false
                    )
                }
                viewModelScope.launch {
                    SearchQueryChangesFlow.emit(event.query)
                }
            }

            ChatsContract.Event.Refresh -> {
                setState { copy(isRefreshing = true, isEmptyResults = false) }
                setEvent(ChatsContract.Event.LoadChats)
            }

            ChatsContract.Event.OnLoading -> setState {
                copy(
                    isLoading = true,
                    error = null,
                    isEmptyResults = false
                )
            }

            is ChatsContract.Event.OnError -> setState {
                copy(
                    isLoading = false,
                    error = event.error,
                    isEmptyResults = false
                )
            }

            is ChatsContract.Event.OnDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = false
//                    isEmptyResults = event.isEmptyResults
                )
            }

            ChatsContract.Event.OnEmptyDataLoaded -> setState {
                copy(
                    isLoading = false,
                    isEmptyResults = true
                )
            }

            is ChatsContract.Event.UserItemClicked -> createChat(event.id)
        }
    }


//    private fun getMainPager() = Pager(
//        config = PagingConfig(pageSize = 10),
////        pagingSourceFactory = { ChatsPagingSource(repository, { event -> setEvent(event) }) }
//    ).flow.cachedIn(viewModelScope)

    private fun getSearchPager() = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            SearchPagingSource(
                repository,
                viewState.value.searchQuery,
                { event -> setEvent(event) })
        }
    ).flow.cachedIn(viewModelScope)


    private fun createChat(userId: Int) {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
                }
            },
            request = {


                repository.createChat(userId)

            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val chat = response.data.data.toChat()
                        viewModelScope.launch {
                            repository.insertChat(chat)
                        }
                        setEvent(ChatsContract.Event.ChatItemClicked(chat.id))
                    }

                    is ApiResponse.Failure -> setState {
                        copy(
                            error = response.errorMessage,
                            isLoading = false
                        )
                    }

                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }


                }
            }
        )
    }


//    private fun getUsers() {
//        mJob?.cancel()
//        val query = viewState.value.searchQuery
//
//
//        baseRequest(
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    setState { copy(error = message) }
//                }
//            },
//            request = {
//
//
//                repository.searchUsers(query, 10, 10)
//
//            },
//            onSuccess = { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//                        val chats = response.data.data.toChats()
//                        setState {
//                            copy(
//                                chats = chats,
//                                error = null,
//                                isLoading = false,
//                                isRefreshing = false,
//                                isEmptyResults = chats.isEmpty()
//
//                            )
//                        }
//                    }
//
//                    is ApiResponse.Failure -> setState {
//                        copy(
//                            error = response.errorMessage,
//                            isLoading = false
//                        )
//                    }
//
//                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
//
//
//                }
//            }
//        )
//
//    }


//    private fun getChats() {
//        mJob?.cancel()
//        baseRequest(
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    setState { copy(error = message) }
//                }
//            },
//            request = {
//                repository.getChats(10, 10)
//            },
//            onSuccess = { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//                        val chats = response.data.data.toChats()
//                        setState {
//                            copy(
//                                chats = chats,
//                                error = null,
//                                isLoading = false,
//                                isRefreshing = false,
//                                isEmptyResults = chats.isEmpty()
//
//                            )
//                        }
//                    }
//
//                    is ApiResponse.Failure -> setState {
//                        copy(
//                            error = response.errorMessage,
//                            isLoading = false
//                        )
//                    }
//
//                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
//
//
//                }
//            }
//        )
//    }
}


package com.example.chats.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.chats.presentation.ChatsContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import androidx.paging.compose.LazyPagingItems
import com.example.chats.presentation.ui.component.ChatItem
import com.example.chats.presentation.ui.component.EmptyListPreview
import com.example.chats.presentation.ui.component.ErrorPreview
import com.example.chats.presentation.ui.component.LoadingPreview
import com.example.chats.presentation.ui.component.SearchTextField
import com.example.core.mLog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    state: ChatsContract.State,
    effectFlow: Flow<ChatsContract.Effect>?,
    onEventSent: (event: ChatsContract.Event) -> Unit,
    onNavigationRequested: (ChatsContract.Effect.Navigation) -> Unit,
) {
    val chats = state.chatsPagingFlow?.collectAsLazyPagingItems()

    val isRefreshing = chats?.loadState?.refresh is LoadState.Loading

    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ChatsContract.Effect.Navigation.ToChat -> {
                    onNavigationRequested(
                        ChatsContract.Effect.Navigation.ToChat(effect.chatId)
                    )
                }



            }
        }?.collect()
    }
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchTextField(state.searchQuery) { newValue ->
            onEventSent(ChatsContract.Event.SearchQueryChanged(newValue))
        }

//        Spacer(modifier = Modifier.height(16.dp))
//        val isRefreshing = chats?.loadState.refresh is LoadState.Loading
        val pullState = rememberPullToRefreshState()
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onEventSent(ChatsContract.Event.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            state = pullState,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    color = MaterialTheme.colorScheme.primary,
                    state = pullState
                )
            }
        ) {

            when {
                state.error != null -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorPreview(state.error)
                    }
                }

                state.error == null -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (chats != null) {
                            items(chats.itemCount ) { index ->
                                chats?.get(index)?.let {
                                    ChatItem(
                                        chat = it,
                                        onClick = {
                                            mLog("1","1")
                                            if(state.isUserList){
                                                onEventSent(ChatsContract.Event.UserItemClicked(it.id))

                                            }
                                            else{
                                                onEventSent(ChatsContract.Event.ChatItemClicked(it.id))

                                            }


                                        }
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
                                    )
                                }
                            }
                        }

                    }
                }
//                state.isEmptyResults -> {
//                    Box(
//                        Modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState()),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        EmptyListPreview()
//                    }
//                }

            }
        }

        chats?.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    onEventSent(ChatsContract.Event.OnLoading)
                    //                            item { LoadingItem() }
                }

                is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    e.error.localizedMessage?.let { ChatsContract.Event.OnError(it) }
                        ?.let { onEventSent(it) }
                    //                            item {
                    //                                ErrorItem(message = e.error.localizedMessage ?: "Unknown error")
                    //                            }
                }

                is LoadState.NotLoading -> onEventSent(ChatsContract.Event.OnDataLoaded)
            }
        }


    }
}



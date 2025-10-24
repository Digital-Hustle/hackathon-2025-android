package com.example.chat.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.chat.presentation.ChatContract
import com.example.chat.presentation.ui.component.MessageItem
import com.example.chat.presentation.ui.component.ProfileItem
import com.example.chats_holder.data.local.entities.MessageEntity
import com.example.core.mLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.util.Date
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatContract.State,
    effectFlow: Flow<ChatContract.Effect>?,
    onEventSent: (event: ChatContract.Event) -> Unit,
    onNavigationRequested: (ChatContract.Effect.Navigation) -> Unit,
) {
//    val messages = state.messagesPagingFlow?.collectAsLazyPagingItems()
    val messages = state.messagesPagingFlow?.collectAsLazyPagingItems()
    mLog("MESS",messages?.itemCount.toString())
//    val messages = List(50) { i ->
//        MessageEntity(
//            id = 1,
//            chatId = 1,
//            content = "Привет ".repeat(Random.nextInt(1, 17)) + i.toString(),
//            senderId = 1,
//
//            timestamp = Date().time,
//            isMyMessage = Random.nextBoolean(),
//        )
//    }
    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ChatContract.Effect.Navigation.ToChats -> {
                    onNavigationRequested(
                        ChatContract.Effect.Navigation.ToChats
                    )
                }

                is ChatContract.Effect.Navigation.ToProfile -> state.secondUserName?.let {
                    ChatContract.Effect.Navigation.ToProfile(
                        it
                    )
                }
                    ?.let {
                        onNavigationRequested(
                            it
                        )
                    }
            }
        }?.collect()



    }



    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {


        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = {
                onEventSent(ChatContract.Event.BackButtonClicked)
            }, Modifier.fillMaxHeight()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }
        }, title = {
            ProfileItem(state.chat) {
                onEventSent(ChatContract.Event.ProfileClicked)
            }

        }, colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
            scrolledContainerColor = MaterialTheme.colorScheme.onTertiary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, reverseLayout = true
        ) {
            if (messages != null) {
                items(messages.itemCount) { index ->
                    messages[index]?.let {
                        MessageItem(
                            message = it,
//                            onClick = {
//                                if (state.isUserList) {
//                                    onEventSent(ChatsContract.Event.UserItemClicked(it.id))
//                                } else {
//                                    onEventSent(ChatsContract.Event.ChatItemClicked(it.id))
//                                }
//                            }
                        )
//                        HorizontalDivider(
//                            modifier = Modifier.fillMaxWidth(0.9f),
//                            thickness = 1.dp,
//                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
//                        )
                    }
                }
            }

        }
        Column(Modifier.background(MaterialTheme.colorScheme.onTertiary)) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = state.inputText,
                    onValueChange = { newValue ->
                        onEventSent(
                            ChatContract.Event.TextInputChanged(
                                newValue
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    placeholder = { Text("Введите сообщение") },
                    maxLines = 4,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiary
                    )
                )

                IconButton(
                    onClick = {
                        if (state.inputText.isNotEmpty()) {
                            onEventSent(
                                ChatContract.Event.OnSendMessage
                            )
                        }
                    }, modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Отправить"
                    )
                }
            }
            Spacer(Modifier.fillMaxHeight(0.05f))
        }

    }
}
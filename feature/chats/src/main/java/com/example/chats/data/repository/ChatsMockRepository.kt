package com.example.chats.data.repository


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.chats.data.paging.MockChatsPagingSource
import com.example.chats.domain.repository.ChatsRepository
import com.example.chats_holder.data.local.dao.ChatDao
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.domain.model.chats.ChatResponse
import com.example.chats_holder.domain.model.chats.CreateChatResponse
import com.example.chats_holder.domain.model.chats.SearchUsersResponse
import com.example.chats_holder.domain.model.chats.UserResponse
import com.example.core.mLog
import com.example.network.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatsMockRepository @Inject constructor(
    private val chatDao: ChatDao,
) : ChatsRepository {

    val names = listOf(
        "Алексей",
        "Мария",
        "Иван",
        "Ольга",
        "Дмитрий",
        "Екатерина",
        "Сергей",
        "Анна",
        "Николай",
        "Юлия"
    )




    val users = List(1000) { i ->
        UserResponse(
            id = i,
            name = "$i user" + " " + names[i % 10],
            photo = null
        )
    }


    override fun getChats(): Flow<PagingData<ChatEntity>> {
        mLog("dada","a")
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MockChatsPagingSource() }
        ).flow
    }

    override fun searchUsers(
        query: String,
        page: Int,
        size: Int
    ): Flow<ApiResponse<SearchUsersResponse>> = flow {
        Log.d("mLOgCHATSEARCH", "ЗАПРОС$page $size")

        val filteredUsers = users.filter { it.name?.contains(query) == true }
        val fromIndex = page * size
        val toIndex = minOf(fromIndex + size, filteredUsers.size)

        val newList = if (fromIndex >= filteredUsers.size) {
            emptyList()
        } else {
            filteredUsers.subList(fromIndex, toIndex)
        }
        delay(1000)

        emit(
            ApiResponse.Success(
                SearchUsersResponse(
                    data = newList,
                    message = ""
                )
            )
        )

    }


    override fun createChat(userId: Int): Flow<ApiResponse<CreateChatResponse>> = flow {
        emit(
            ApiResponse.Success(
                CreateChatResponse(
                    data = ChatResponse(userId, "Алина", null),
                    message = ""
                )
            )
        )

    }

    override suspend fun insertChat(chat: ChatEntity) {
        chatDao.insertChat(chat)
    }

}

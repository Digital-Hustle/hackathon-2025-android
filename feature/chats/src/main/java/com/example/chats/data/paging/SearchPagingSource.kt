package com.example.chats.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chats.data.mapper.toChatsFromUsers
import com.example.chats.presentation.ChatsContract
import com.example.chats.domain.repository.ChatsRepository
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SearchPagingSource(
    private val repository: ChatsRepository,
    private val query: String,
    private val onEventSent: (event: ChatsContract.Event) -> Unit
) : PagingSource<Int, ChatEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatEntity> {
        val page = params.key ?: 0

        return try {
            val response = repository.searchUsers(query, page, params.loadSize)
                .filter { it !is ApiResponse.Loading } // Пропускаем состояние Loading
                .first() // Берем первый значимый результат
            when (response) {
                is ApiResponse.Success -> {
                    var chats: List<ChatEntity>

                    withContext(Dispatchers.IO) {
                        chats = response.data.data.toChatsFromUsers()

                    }
                    onEventSent(
                        if (chats.isEmpty() && page == 0) {
                            ChatsContract.Event.OnEmptyDataLoaded
                        } else {
                            ChatsContract.Event.OnDataLoaded
                        }
                    )


                    delay(500)

                    LoadResult.Page(
                        data = chats,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (chats.isEmpty()) null else page + 1
                    )
                }

                is ApiResponse.Failure -> {
                    onEventSent(ChatsContract.Event.OnError(response.errorMessage))
                    LoadResult.Error(Exception(response.errorMessage))
                }

                is ApiResponse.Loading -> {
                    onEventSent(ChatsContract.Event.OnLoading)
                    LoadResult.Page(emptyList(), null, null)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChatEntity>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}
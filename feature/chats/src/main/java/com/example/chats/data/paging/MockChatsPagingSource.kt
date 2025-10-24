package com.example.chats.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chats_holder.data.local.entities.ChatEntity
import com.example.chats_holder.data.mapper.toChatEntities
import com.example.chats_holder.domain.model.chats.ChatResponse

class MockChatsPagingSource(
) : PagingSource<Int, ChatEntity>() {

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


    val list = List(15) { i ->
        ChatResponse(
            id = i,
            name = i.toString() + " " + names[i % 10],
            photo = null

        )
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatEntity> {
        val page = params.key ?: 0
        val fromIndex = page * params.loadSize
        val toIndex = minOf(fromIndex + params.loadSize, list.size)

        val newList = if (fromIndex >= list.size) {
            emptyList()
        } else {
            list.subList(fromIndex, toIndex)
        }.toChatEntities()
        return try {

            LoadResult.Page(
                data = newList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (newList.isEmpty()) null else page + 1
            )


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
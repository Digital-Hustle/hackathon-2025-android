//package com.example.chat.data.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.chats_holder.data.local.entities.MessageEntity
//import com.example.chats_holder.data.mapper.toMessageEntities
//import com.example.chats_holder.domain.model.messages.MessageResponse
//import java.util.Date
//
//class MockMessagesPagingSource(
//    private val userId: Int, private val chatId: Int,
//) : PagingSource<Int, MessageEntity>() {
//
//    val texts = listOf(
//        "Привет",
//        "Как дела",
//        "Займи тыщу",
//        "Видел биток упал?",
//        "Да",
//        "Z",
//        "Пока",
//        "Ну ты муравей",
//        "2500",
//        "Почему"
//
//    )
//
//
//    val list = List(100) { i ->
//        MessageResponse(
//            id = i,
//            userId = userId + listOf(0, 1).random(),
//            text = texts.random(),
//            timestamp = Date().time,
//        )
//    }
//
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MessageEntity> {
//        val page = params.key ?: 0
//        val fromIndex = page * params.loadSize
//        val toIndex = minOf(fromIndex + params.loadSize, list.size)
//
//        val newList = if (fromIndex >= list.size) {
//            emptyList()
//        } else {
//            list.subList(fromIndex, toIndex)
//        }.toMessageEntities(chatId,userId)
//        return try {
//
//            LoadResult.Page(
//                data = newList,
//                prevKey = if (page == 0) null else page - 1,
//                nextKey = if (newList.isEmpty()) null else page + 1
//            )
//
//
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MessageEntity>): Int? {
//        return state.anchorPosition?.let { pos ->
//            state.closestPageToPosition(pos)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
//        }
//    }
//}
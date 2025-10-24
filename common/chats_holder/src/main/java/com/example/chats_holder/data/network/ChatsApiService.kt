package com.example.chats_holder.data.network


import com.example.chats_holder.domain.model.chats.SearchUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import com.example.chats_holder.domain.model.chats.CreateChatResponse
import com.example.chats_holder.domain.model.chats.GetChatsResponse
import com.example.chats_holder.domain.model.messages.GetMessagesResponse
import retrofit2.http.Path


interface ChatsApiService {
    @POST("/api/v1/chats/create")
    suspend fun createChat(
        @Query("secondUserId") userId: Int,
    ): Response<CreateChatResponse>


    @GET("/api/v1/chats")
    suspend fun getChats(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetChatsResponse>

    @GET("/api/v1/users/search")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchUsersResponse>


    @GET("/api/v1/messages/chat/{chatId}")
    suspend fun getMessages(
        @Path("chatId") chatId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetMessagesResponse>


}
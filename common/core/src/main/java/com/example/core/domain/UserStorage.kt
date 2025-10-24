package com.example.core.domain

import kotlinx.coroutines.flow.Flow

interface UserStorage {

    suspend fun setId(id:Int)
    fun getId(): Flow<Int?>
    suspend fun setUsername(username:String)
    fun getUsername():Flow<String?>

    suspend fun setName(name:String)
    fun getName():Flow<String?>
    suspend fun clear()

}
package com.sueta.core.domain

import kotlinx.coroutines.flow.Flow

interface UserStorage {

    suspend fun setId(id: String)
    fun getId(): Flow<String?>

    suspend fun setUsername(username: String)
    fun getUsername(): Flow<String?>

    suspend fun setCategories(categories: Set<String>)
    fun getCategories(): Flow<Set<String>?>

    suspend fun setName(name: String)
    fun getName(): Flow<String?>

    suspend fun clear()

}
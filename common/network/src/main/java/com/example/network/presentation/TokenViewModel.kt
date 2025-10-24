package com.example.network.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.storage.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,

) : ViewModel() {

//    init {
//        deleteToken()
//        deleteRefresh()
//    }

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _refresh = MutableStateFlow<String?>(null)
    val refresh: StateFlow<String?> = _refresh.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getToken().collect {
                _token.value = it
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token)
        }
    }

    fun saveRefresh(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveRefreshToken(token)
        }
    }

    fun saveTokens(access:String,refresh:String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveRefreshToken(refresh)
            tokenManager.saveToken(access)
        }
    }

    fun deleteRefresh(){
        viewModelScope.launch(Dispatchers.IO){
            tokenManager.deleteRefreshToken()
        }
    }


    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }
}
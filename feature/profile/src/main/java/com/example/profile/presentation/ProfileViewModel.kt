package com.example.profile.presentation

import android.net.Uri
import android.util.Log
import com.example.core.presentation.BaseViewModel
import com.example.core.presentation.CoroutinesErrorHandler
import com.example.network.ApiResponse
import com.example.network.presentation.ImageManager
import com.example.profile.data.mapper.toProfile
import com.example.profile.data.mapper.toRequest
import com.example.profile.domain.repository.ProfileRepository
import com.example.profile.presentation.model.Profile
import com.example.profile.presentation.model.Sex
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val imageManager: ImageManager,
) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {
    override fun setInitialState(): ProfileContract.State = ProfileContract.State()

    override fun handleEvents(event: ProfileContract.Event) {
        when (event) {
            is ProfileContract.Event.LoadProfile -> {
//                setState { copy(isLoading = true, imageIsLoading = true) }
                getProfile(event.username)
            }

            ProfileContract.Event.BackButtonClicked -> setEffect {
                ProfileContract.Effect.Navigation.ToMain
            }

            is ProfileContract.Event.AgeChanged -> setState { copy(profile = profile.copy(age = event.query)) }
            is ProfileContract.Event.DescriptionChanged -> setState {
                copy(
                    profile = profile.copy(
                        description = event.query
                    )
                )
            }

            ProfileContract.Event.ImageChangeButtonClicked -> setState {
                copy(
                    imagePickerDialogIsOpen = true
                )
            }

            is ProfileContract.Event.NameChanged -> setState { copy(profile = profile.copy(name = event.query)) }
            is ProfileContract.Event.SexChanged -> setState {
                copy(
                    profile = profile.copy(
                        sex = Sex.fromId(
                            event.query
                        ) ?: Sex.MALE
                    )
                )
            }

            ProfileContract.Event.EditIsOn -> setState { copy(isEdit = true) }
            ProfileContract.Event.EditIsOff -> setState { copy(isEdit = false) }
            is ProfileContract.Event.ImageSelected -> uploadImage(event.uri)
            ProfileContract.Event.DismissImagePickerDialog -> setState {
                copy(
                    imagePickerDialogIsOpen = false
                )
            }

            is ProfileContract.Event.SaveProfile -> editProfile(event.profile)
            ProfileContract.Event.OnDismissMenu -> setState { copy(showMenu = false) }
            ProfileContract.Event.OnMenuBottonClicked ->setState { copy(showMenu = true) }
            ProfileContract.Event.Logout -> setEffect { ProfileContract.Effect.Logout }
        }
    }




    private fun getProfile(username: String?) {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = {
                repository.getProfile(username)
            },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        setState { copy(profile = response.data.toProfile(), isLoading = false) }
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState {
                        copy(
                            isLoading = true,
                            error = null,
                            imageIsLoading = true
                        )
                    }
                }
            }
        )
    }

    private fun editProfile(profile: Profile) {
        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = { repository.editProfile(profile = profile.toRequest()) },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        setState { copy(profile = response.data.toProfile(), isLoading = false) }
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState {
                        copy(
                            isLoading = true,
                            error = null,
                            imageIsLoading = true
                        )
                    }
                }
            }
        )
    }

    private fun uploadImage(imageUri: Uri) {
        val imagePart = imageManager.prepareImageForUpload(imageUri)

        Log.d("mLogIMG","TYT")

        baseRequest(
            errorHandler = object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
                }
            },
            request = { repository.uploadImage(imagePart) },
            onSuccess = { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Log.d("mLogIMG",response.data.data.toString())
                        setState {
                            copy(
                                profile = profile.copy(
                                    image = response.data.data?.let {
                                        ImageManager.base64toBitmap(
                                            it
                                        )
                                    }
                                )
                            )
                        }
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                    }

                    is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                    is ApiResponse.Loading -> setState { copy(isLoading = true, error = null) }
                }
            }
        )
    }

    fun setUsername(username: String) {
        setState { copy(username = username) }
    }


//    private val _profile = MutableStateFlow<ApiResponse<Profile>>(ApiResponse.Loading)
//    val profile = _profile
//
//    private val _image = MutableStateFlow<ApiResponse<ImageResponse>>(ApiResponse.Loading)
//    val image= _image
//
//    fun getProfile(username: String?, coroutinesErrorHandler: CoroutinesErrorHandler) =
//        baseRequest(
//            _profile, coroutinesErrorHandler
//        ) {
//            repository.getProfile(username)
//        }
//
//    fun editProfile(profile: Profile,coroutinesErrorHandler: CoroutinesErrorHandler) =
//        baseRequest(
//            _profile,coroutinesErrorHandler
//        ){
//            repository.editProfile(profile)
//        }
//
//    fun uploadImage(imageUri: Uri,coroutinesErrorHandler: CoroutinesErrorHandler){
//        val imagePart = imageManager.prepareImageForUpload( imageUri)
//
//        // Подготавливаем текстовые данные
////        val descriptionBody = "profile".toRequestBody("text/plain".toMediaTypeOrNull())
//
//
//        return baseRequest(
//            _image,coroutinesErrorHandler
//        ){
//
//            repository.uploadImage(imagePart)
//        }
//    }
//
//    fun base64ToBitmap(res:String) = imageManager.Base64toBitmap(res)


}
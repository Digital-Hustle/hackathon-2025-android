package com.sueta.profile.presentation

import android.net.Uri
import com.sueta.core.presentation.ViewEvent
import com.sueta.core.presentation.ViewSideEffect
import com.sueta.core.presentation.ViewState
import com.sueta.profile.presentation.model.Interest
import com.sueta.profile.presentation.model.Profile

class ProfileContract {

    sealed class Event : ViewEvent {

        data class OnNameChanged(val query: String) : Event()
        data class OnSurnameChanged(val query: String) : Event()
        data class OnBirthDateChanged(val query: String) : Event()
        data class OnInterestsChanged(val interest: Interest) : Event()

        data class LoadProfile(val username: String?) : Event()

        object BackButtonClicked : Event()
        object ImageChangeButtonClicked : Event()
        object DismissImagePickerDialog : Event()

        object EditIsOn : Event()
        object EditIsOff : Event()

        data class ImageSelected(val uri: Uri) : Event()

        data class SaveProfile(val profile: Profile) : Event()

        data object OnMenuBottonClicked : Event()
        data object OnDismissMenu : Event()
        data object Logout : Event()


    }

    data class State(
        val profile: Profile = Profile(),
        val username: String? = null,
        val isEdit: Boolean = false,

        val error: String? = null,
        val isLoading: Boolean = false,
        val imageIsLoading: Boolean = false,
        val imagePickerDialogIsOpen: Boolean = false,
        val showMenu: Boolean = false

    ) : ViewState

    sealed class Effect : ViewSideEffect {
        //        object ProfileWasLoaded : Effect()
        object Logout : Effect()


        sealed class Navigation : Effect() {
            object ToMain : Navigation()
        }
    }

}
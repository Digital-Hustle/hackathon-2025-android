package com.example.auth.data.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.example.core.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class GoogleManager(private val context: Context) {
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(
            BuildConfig.WEB_CLIENT_ID)
        .setAutoSelectEnabled(false)

//        .setNonce(UUID.randomUUID().toString())
        .build()

    private val signInWithGoogleOption =
        GetSignInWithGoogleOption.Builder(
            BuildConfig.WEB_CLIENT_ID)
            .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .addCredentialOption(signInWithGoogleOption)
        .build()

    private val credentialManager = CredentialManager.create(context)


    fun signIn(): String =

        runBlocking(Dispatchers.Main) {
            try {
                val credentialResult = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                handleSignIn(credentialResult)
            } catch (e: Exception) {
                Log.e("AuthActivity", "Credential retrieval failed", e)
                ""
            }
        }


    private fun handleSignIn(result: GetCredentialResponse): String {
        when (val credential = result.credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                Log.d("AuthActivity", "Received PublicKeyCredential: $responseJson")
                // Handle PublicKeyCredential if necessary
            }

            is PasswordCredential -> {
                //val username = credential.id
                //val password = credential.password
                // Handle password credential
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

//                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                        Log.d("mLogToken", googleIdTokenCredential.idToken)
                        return googleIdTokenCredential.idToken
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("AuthActivity", "Received an invalid google id token response", e)
                    }
                } else {
                    Log.e("AuthActivity", "Unexpected type of credential")
                    return ""
                }
            }

            else -> {
                Log.e("AuthActivity", "Unsupported credential type")
                return ""
            }
        }
        return ""
    }


}
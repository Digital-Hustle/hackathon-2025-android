package com.sueta.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sueta.presentation.ui.ForexTheme
import com.example.navigation.AppNavigation
import com.example.navigation.Navigation
import com.example.network.presentation.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tokenViewModel: TokenViewModel by viewModels()
//    private val viewModel : ChatsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        Log.d("mLOGGAZ",viewModel.test2())
//
//        Log.d("mLOGGAZ",viewModel.test())
//        tokenViewModel.deleteToken()
//        tokenViewModel.deleteRefresh()

        var keepSplashScreen = true
        installSplashScreen().apply {
            setKeepOnScreenCondition { keepSplashScreen }
        }
        var finalToken: String? = null
        runBlocking {
            withTimeoutOrNull(1000) {
                val firstValue = tokenViewModel.token.first()

                if (firstValue != null) {
                    finalToken = firstValue
                } else {
                    withTimeoutOrNull(500) {
                        tokenViewModel.token.drop(1).firstOrNull()?.let {
                            finalToken = it
                        }
                    }
                }
            }
        }
        val startDestination = if (finalToken != null) Navigation.Routes.MAIN else Navigation.Routes.AUTH
        keepSplashScreen = false


        enableEdgeToEdge()

        setContent {
            ForexTheme {
                Surface {
                    Box(Modifier.fillMaxSize()) {
                        AppNavigation(startDestination)
                    }
                }
            }

        }
    }



}
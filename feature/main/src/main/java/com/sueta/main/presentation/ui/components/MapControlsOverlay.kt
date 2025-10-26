package com.sueta.main.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.MainContract
import ru.dgis.sdk.compose.map.controls.MyLocationComposable
import ru.dgis.sdk.compose.map.controls.TrafficComposable
import ru.dgis.sdk.compose.map.controls.ZoomComposable

@Composable
fun MapControlsOverlay(
    state: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit,
    controlsIsVisible: Boolean
) {
    val map by state.map.collectAsState()


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
        Row(
            modifier = Modifier
                .padding(
                    top = 50.dp, end = 30.dp, start = 30.dp, bottom = 20.dp
                )
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary),
                onClick = { onEventSent(MainContract.Event.OnHistoryButtonCLicked) }) {
                Icon(Icons.Default.Bookmark, "История")
            }

            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary),
                onClick = { onEventSent(MainContract.Event.OnProfileButtonCLicked) }) {
                Icon(Icons.Default.Person, "Профиль")
            }
        }
        Row(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            map?.let {

                Column(
                    modifier = Modifier.padding(bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TrafficComposable(map!!)
                }
            }
        }
        AnimatedVisibility(
            visible = controlsIsVisible,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 30.dp)
                .fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                map?.let {
                    Column(
                        modifier = Modifier.padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ZoomComposable(map!!)
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 40.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MyLocationComposable(map!!)
                    }
                }

            }
        }
    }
}
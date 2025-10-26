package com.sueta.main.presentation.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sueta.main.data.mapper.toStringAddress
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TransportType
import com.sueta.main.presentation.model.TravelStyle
import com.sueta.main.presentation.ui.components.FilterChip
import com.sueta.main.presentation.ui.components.PlaceItem
import com.sueta.main.presentation.ui.components.PointSelectionCards
import com.sueta.main.presentation.ui.components.TransportTypeChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouteBottomSheetContent(
    state: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp), contentAlignment = Alignment.TopCenter
    ) {


        Column {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
                    .align(Alignment.CenterHorizontally)
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {

                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary),
                    onClick = { onEventSent(MainContract.Event.RouteEvent.OnRouteBottomSheetBackPressed) }) {
                    Icon(Icons.Default.ArrowBackIosNew, "Назад")
                }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Откуда:" + state.pointSelectionState.startPoint?.toStringAddress(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "Куда:" + state.pointSelectionState.endPoint?.toStringAddress(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                TransportType.entries.forEach {
                    TransportTypeChip(
                        transportType = it,
                        isSelected = state.routeBottomSheetState.selectedTransportType == it,
                        onClick = {
                            onEventSent(
                                MainContract.Event.RouteEvent.OnTransportTypeClicked(
                                    it
                                )
                            )
                        })
                }
            }

            Text(
                text = "Места",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            LazyColumn {
                items(state.routeBottomSheetState.route?.places ?: emptyList()) { item ->
                    PlaceItem(
                        item,
                        onClick = {
                            onEventSent(
                                MainContract.Event.RouteEvent.OnPlaceItemClicked(
                                    item
                                )
                            )
                        },
                    )
                }
            }


            Button(
                onClick = { onEventSent(MainContract.Event.OnGoButtonCLicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                Text("GO")
            }

        }
    }
}
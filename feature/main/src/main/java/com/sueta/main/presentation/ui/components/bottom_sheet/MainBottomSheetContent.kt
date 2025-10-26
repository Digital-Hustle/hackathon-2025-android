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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TravelStyle
import com.sueta.main.presentation.ui.components.FilterChip
import com.sueta.main.presentation.ui.components.PointSelectionCards

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainBottomSheetContent(
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
            Button(
                onClick = { onEventSent(MainContract.Event.OnGoButtonCLicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                Text("Построить маршрут")
            }
            PointSelectionCards(
                state = state.pointSelectionState,
                onPointClick = { pointType ->
                    onEventSent(
                        MainContract.Event.OnSelectPointButtonClicked(
                            pointType
                        )
                    )
                },
            )

            Text(
                text = "Бюджет поездки",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                BudgetType.entries.forEach {
                    FilterChip(
                        text = it.value,
                        isSelected = state.bottomSheetState.selectedBudget == it,
                        onClick = { onEventSent(MainContract.Event.OnBudgetClicked(it)) })
                }


            }
            Text(
                text = "Стиль путешествия",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                TravelStyle.entries.forEach {
                    FilterChip(
                        text = it.value,
                        isSelected = state.bottomSheetState.selectedStyle == it,
                        onClick = {
                            onEventSent(
                                MainContract.Event.OnTravelStyleClicked(
                                    it
                                )
                            )
                        })
                }
            }
            Text(
                text = "Тип маршрута",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                RouteType.entries.forEach {
                    FilterChip(
                        text = it.value,
                        isSelected = state.bottomSheetState.selectedRouteType == it,
                        onClick = { onEventSent(MainContract.Event.OnRouteTypeClicked(it)) })
                }
            }
            Spacer(Modifier.fillMaxHeight(0.1f))
        }
    }
}
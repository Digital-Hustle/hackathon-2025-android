package com.sueta.main.presentation.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.RouteType
import com.sueta.main.presentation.ui.components.BudgetType
import com.sueta.main.presentation.ui.components.FilterChip
import com.sueta.main.presentation.ui.components.PointDetailsBottomSheet
import com.sueta.main.presentation.ui.components.PointSelectionBottomSheet
import com.sueta.main.presentation.ui.components.PointSelectionCards
import com.sueta.main.presentation.ui.components.TravelStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dgis.sdk.compose.map.MapComposable
import ru.dgis.sdk.compose.map.controls.MyLocationComposable
import ru.dgis.sdk.compose.map.controls.TrafficComposable
import ru.dgis.sdk.compose.map.controls.ZoomComposable
import ru.dgis.sdk.map.RenderedObjectInfo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    state: MainContract.State,
    effectFlow: Flow<MainContract.Effect>?,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    val map by state.mapState.map.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded // или Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                MainContract.Effect.Navigation.toSelfProfile -> onNavigationRequested(MainContract.Effect.Navigation.toSelfProfile)

                MainContract.Effect.Navigation.toHistory -> TODO()
                MainContract.Effect.PartiallyExpandBottomSheet -> bottomSheetState.partialExpand()
                MainContract.Effect.ExpandBottomSheet -> bottomSheetState.expand()
                MainContract.Effect.PointsNotSelected -> Toast(context,"Выберите точки маршрута!",Toats.LENGHT_LONG)
            }
        }?.collect()


    }
    LaunchedEffect(state.mapState) {
        state.mapState.objectTappedCallback = {
            selectedObject = it
            coroutineScope.launch {
                onEventSent(MainContract.Event.OnMapObjectClicked(it.item))
            }

        }
    }

    if (state.showPointSelectBottomSheet) {
        PointSelectionBottomSheet(
            state = state.pointSelectionState,
            onSearchQueryChange = { query ->
                onEventSent(
                    MainContract.Event.PointSelectionEvent.OnSearchChanged(
                        query
                    )
                )
            },
            onMapSelectionSelected = {
                onEventSent(MainContract.Event.PointSelectionEvent.OnMapSelectionSelected)
            },
            onSearchResultSelected = { pointType, point ->
                onEventSent(
                    MainContract.Event.PointSelectionEvent.OnSearchSelectionSelected(
                        pointType, point
                    )
                )
            },
            onDismiss = { onEventSent(MainContract.Event.OnDismissPointSelectBottomSheet) },
        )
    }
    if (state.showDetailsBottomSheet) {
        PointDetailsBottomSheet(
            point = state.selectedPoint,
            onDismiss = { onEventSent(MainContract.Event.OnDismissPointDetailsBottomSheet) },
            modifier = Modifier
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
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
                            onClick = { /* действие */ },
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
                                    onClick = { onEventSent(MainContract.Event.BudgetClicked(it)) })
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
                                    onClick = { onEventSent(MainContract.Event.TravelStyleClicked(it)) })
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
                                    onClick = { onEventSent(MainContract.Event.RouteTypeClicked(it)) })
                            }
                        }
                        Spacer(Modifier.fillMaxHeight(0.1f))
                    }
                }
            },
            sheetPeekHeight = 100.dp,
            sheetShadowElevation = 8.dp,
            sheetDragHandle = {},
            sheetTonalElevation = 0.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MapComposable(state = state.mapState)


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
                            onClick = { onEventSent(MainContract.Event.ProfileButtonCLicked) }) {
                            Icon(Icons.Default.Bookmark, "История")
                        }

                        IconButton(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiary),
                            onClick = { onEventSent(MainContract.Event.ProfileButtonCLicked) }) {
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
                        visible = bottomSheetState.currentValue == SheetValue.PartiallyExpanded,
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
        }
    }
}
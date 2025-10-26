package com.sueta.main.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sueta.main.presentation.MainContract
import com.sueta.main.presentation.ui.components.MapControlsOverlay
import com.sueta.main.presentation.ui.components.bottom_sheet.BaseBottomSheetScaffold
import com.sueta.main.presentation.ui.components.bottom_sheet.HistoryDetailsBottomSheet
import com.sueta.main.presentation.ui.components.bottom_sheet.MainBottomSheetContent
import com.sueta.main.presentation.ui.components.bottom_sheet.PlaceDetailsBottomSheet
import com.sueta.main.presentation.ui.components.bottom_sheet.PointDetailsBottomSheet
import com.sueta.main.presentation.ui.components.bottom_sheet.PointSelectionBottomSheet
import com.sueta.main.presentation.ui.components.bottom_sheet.RouteBottomSheetContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.dgis.sdk.compose.map.MapComposable

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    state: MainContract.State,
    effectFlow: Flow<MainContract.Effect>?,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded // или Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )


    val routeBottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded // или Collapsed
    )
    val routeScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                MainContract.Effect.Navigation.toSelfProfile -> onNavigationRequested(MainContract.Effect.Navigation.toSelfProfile)

                MainContract.Effect.Navigation.toHistory -> TODO()
                MainContract.Effect.PartiallyExpandBottomSheet -> bottomSheetState.partialExpand()
                MainContract.Effect.ExpandBottomSheet -> bottomSheetState.expand()
                MainContract.Effect.PointsNotSelected -> {
                    Toast.makeText(context, "Выберите точки маршрута!", Toast.LENGTH_LONG).show()
                }
            }
        }?.collect()


    }
    LaunchedEffect(state.mapState) {
        state.mapState.objectTappedCallback = {
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
                    MainContract.Event.PointSelectionEvent.OnSearchChanged(query)
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

    if (state.showPlaceDetailsBottomSheet) {
        PlaceDetailsBottomSheet(
            state.routeBottomSheetState.selectedPlace,
            onDismiss = { onEventSent(MainContract.Event.RouteEvent.OnDismissPlaceDetailsBottomSheet) },

            )
    }

    if (state.showHistoryBottomSheet) {
        HistoryDetailsBottomSheet(
            state, onEventSent
        )
    }



    Box(modifier = Modifier.fillMaxSize()) {
        BaseBottomSheetScaffold(
            scaffoldState,
            sheetContent = {
                if (state.routeBottomSheetState.route != null) {
                    RouteBottomSheetContent(state, onEventSent)
                } else {
                    MainBottomSheetContent(state, onEventSent)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                MapComposable(state = state.mapState)
                MapControlsOverlay(
                    state = state,
                    onEventSent = onEventSent,
                    controlsIsVisible = bottomSheetState.currentValue == SheetValue.PartiallyExpanded
                )
            }
        }
    }
}
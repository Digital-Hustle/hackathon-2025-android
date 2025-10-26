package com.sueta.main.presentation

import com.sueta.core.presentation.ViewEvent
import com.sueta.core.presentation.ViewSideEffect
import com.sueta.core.presentation.ViewState
import com.sueta.main.presentation.model.BudgetType
import com.sueta.main.presentation.model.Place
import com.sueta.main.presentation.model.PointType
import com.sueta.main.presentation.model.Route
import com.sueta.main.presentation.model.RouteType
import com.sueta.main.presentation.model.TransportType
import com.sueta.main.presentation.model.TravelStyle
import kotlinx.coroutines.flow.StateFlow
import ru.dgis.sdk.compose.map.MapComposableState
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.DirectoryObject
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.Map
import ru.dgis.sdk.map.MapOptions
import ru.dgis.sdk.map.RenderedObject
import ru.dgis.sdk.map.Zoom
import com.sueta.main.presentation.model.Event as ModelEvent

class MainContract {
    sealed class Event : ViewEvent {
        object OnProfileButtonCLicked : Event()
        object OnHistoryButtonCLicked : Event()
        object OnGoButtonCLicked : Event()
        class OnMapObjectClicked(val renderedObject: RenderedObject) : Event()

        object OnDismissPointSelectBottomSheet : Event()
        object OnDismissPointDetailsBottomSheet : Event()
        object OnDismissHistoryBottomSheet : Event()


        data class OnSelectPointButtonClicked(val pointType: PointType) : Event()
        data class OnSearchResult(val result: List<DirectoryObject>) : Event()
        data class OnBudgetClicked(val budgetType: BudgetType) : Event()
        data class OnTravelStyleClicked(val travelStyle: TravelStyle) : Event()
        data class OnRouteTypeClicked(val routeType: RouteType) : Event()

        sealed class PointSelectionEvent : ViewEvent {
            data class OnSearchChanged(val query: String) : Event()
            data class Search(val query: String) : Event()
            data object OnMapSelectionSelected : Event()
            data class OnSearchSelectionSelected(
                val pointType: PointType,
                val point: DirectoryObject
            ) : Event()
        }
        sealed class RouteEvent: ViewEvent{
            data class OnTransportTypeClicked(val transportType: TransportType) : Event()
            data class OnPlaceItemClicked(val place: Place): Event()
            object OnDismissPlaceDetailsBottomSheet : Event()
            object OnRouteBottomSheetBackPressed:Event()
        }

        sealed class HistoryEvent: ViewEvent{
            data class ChangeHistoryTypeButtonClicked(val select: Boolean) : Event()
            data class OnHistoryItemClicked(val route: Route):Event()
        }

//
    }


    data class State(
        val mapOptions: MapOptions = MapOptions().apply {
            position = CameraPosition(
                point = GeoPoint(47.237422, 39.712262),
                zoom = Zoom(12f),
            )
        },
        val mapState: MapComposableState = MapComposableState(mapOptions),
        val map: StateFlow<Map?> = mapState.map,

        val showPointSelectBottomSheet: Boolean = false,
        val pointSelectionState: PointSelectionState = PointSelectionState(),

        val bottomSheetState: BottomSheetState = BottomSheetState(),
        val showDetailsBottomSheet: Boolean = false,

        val showPlaceDetailsBottomSheet: Boolean = false,
        val showEventDetailsBottomSheet: Boolean = false,
        val showHistoryBottomSheet: Boolean = false,


        val routeBottomSheetState: RouteBottomSheetState = RouteBottomSheetState(),
        val historyBottomSheetState: HistoryBottomSheetState = HistoryBottomSheetState(),
        val selectedPoint: DirectoryObject? = null,
        val isPointPickOnMap: Boolean = false,

        val error: String? = null,
        val isLoading: Boolean = false,
    ) : ViewState


    data class BottomSheetState(
        val selectedBudget: BudgetType = BudgetType.ECONOMY,
        val selectedRouteType: RouteType = RouteType.OPTIMAL,
        val selectedStyle: TravelStyle = TravelStyle.CULTURAL
    )

    data class PointSelectionState(
        val selectedPoint: PointType = PointType.NONE,
        val startPoint: DirectoryObject? = null,
        val endPoint: DirectoryObject? = null,
        val searchQuery: String = "",
        val searchResults: List<DirectoryObject> = emptyList(),
        val isSearchMode: Boolean = false
    )

    data class RouteBottomSheetState(
        val route: Route? = null,
        val selectedTransportType: TransportType = TransportType.WALKING,
        val selectedPlace: Place? = null,
        val selectedEvent: ModelEvent? = null
    )

    data class HistoryBottomSheetState(
        val routes :List<Route> = emptyList(),
        val isTopTen : Boolean = false
    )

    sealed class Effect : ViewSideEffect {

        object PartiallyExpandBottomSheet : Effect()
        object ExpandBottomSheet : Effect()
        object PointsNotSelected : Effect()

        sealed class Navigation : Effect() {
            object toSelfProfile : Navigation()
            object toHistory : Navigation()
        }

    }

}







package com.sueta.main.presentation

import com.sueta.core.presentation.ViewEvent
import com.sueta.core.presentation.ViewSideEffect
import com.sueta.core.presentation.ViewState
import com.sueta.main.presentation.ui.components.BudgetType
import com.sueta.main.presentation.ui.components.TravelStyle
import ru.dgis.sdk.compose.map.MapComposableState
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.DirectoryObject
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.MapOptions
import ru.dgis.sdk.map.RenderedObject
import ru.dgis.sdk.map.Zoom

class MainContract {
    sealed class Event : ViewEvent {
        object ProfileButtonCLicked : Event()
        object HistoryButtonCLicked : Event()
        object GoButtonCLicked : Event()
        class OnMapObjectClicked(val renderedObject: RenderedObject) : Event()

        object OnDismissPointSelectBottomSheet : Event()
        object OnDismissPointDetailsBottomSheet : Event()

        data class OnSelectPointButtonClicked(val pointType: PointType) : Event()
        data class OnSearchResult(val result: List<DirectoryObject>) : Event()
        data class BudgetClicked(val budgetType: BudgetType) : Event()
        data class TravelStyleClicked(val travelStyle: TravelStyle) : Event()
        sealed class PointSelectionEvent : ViewEvent {
            data class OnSearchChanged(val query: String) : Event()
            data class Search(val query: String) : Event()
            data object OnMapSelectionSelected : Event()
            data class OnSearchSelectionSelected(
                val pointType: PointType,
                val point: DirectoryObject
            ) : Event()
        }


    }


    data class State(
        val mapOptions: MapOptions = MapOptions().apply {
            position = CameraPosition(
                point = GeoPoint(47.237422, 39.712262),
                zoom = Zoom(12f),
            )
        },
        val showPointSelectBottomSheet: Boolean = false,
        val showDetailsBottomSheet: Boolean = false,
        val selectedPoint : DirectoryObject? = null,
        val isPointPickOnMap: Boolean = false,
        val bottomSheetState: BottomSheetState = BottomSheetState(),
        val mapState: MapComposableState = MapComposableState(mapOptions),
        val error: String? = null,
        val pointSelectionState: PointSelectionState = PointSelectionState(),
    ) : ViewState


    data class BottomSheetState(
        val selectedBudget: BudgetType = BudgetType.ECONOMY,
        val selectedStyles: Set<TravelStyle> = setOf(TravelStyle.CULTURAL)
    )

    sealed class Effect : ViewSideEffect {

        object PartiallyExpandBottomSheet : Effect()
        object ExpandBottomSheet : Effect()

        sealed class Navigation : Effect() {
            object toSelfProfile : Navigation()
            object toHistory : Navigation()
        }
    }

}


enum class PointType {
    NONE, START, END
}


data class PointSelectionState(
    val selectedPoint: PointType = PointType.NONE,
    val startPoint: DirectoryObject? = null,
    val endPoint: DirectoryObject? = null,
    val searchQuery: String = "",
    val searchResults: List<DirectoryObject> = emptyList(),
    val isSearchMode: Boolean = false
)

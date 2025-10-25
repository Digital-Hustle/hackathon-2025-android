package com.sueta.main.presentation

import androidx.lifecycle.viewModelScope
import com.sueta.core.mLog
import com.sueta.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.dgis.sdk.DgisObjectId
import ru.dgis.sdk.Meter
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.SearchManager
import ru.dgis.sdk.directory.SearchQueryBuilder
import ru.dgis.sdk.map.DgisMapObject
import ru.dgis.sdk.map.DgisSource
import ru.dgis.sdk.map.RenderedObject
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(private val searchManager: SearchManager) :
    BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {


    private val SearchQueryChangesFlow = MutableSharedFlow<String>()

    override fun setInitialState(): MainContract.State = MainContract.State()


    init {
        viewModelScope.launch {
            SearchQueryChangesFlow.debounce(500.milliseconds) // Задержка 500 мс
                .collect {
                    setEvent(MainContract.Event.PointSelectionEvent.Search(it))
                }
        }
    }

    override fun handleEvents(event: MainContract.Event) {
        mLog("HANDLE", viewState.value.isPointPickOnMap.toString())
        when (event) {
            MainContract.Event.ProfileButtonCLicked -> setEffect { MainContract.Effect.Navigation.toSelfProfile }

            is MainContract.Event.BudgetClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedBudget = event.budgetType
                    )
                )
            }

            is MainContract.Event.TravelStyleClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedStyle = event.travelStyle
                    )
                )

            }
            is MainContract.Event.RouteTypeClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedRouteType = event.routeType
                    )
                )

            }


            MainContract.Event.GoButtonCLicked -> {}
            MainContract.Event.HistoryButtonCLicked -> setEffect { MainContract.Effect.Navigation.toHistory }
            is MainContract.Event.PointSelectionEvent.OnSearchChanged -> {
                setState {
                    copy(pointSelectionState = pointSelectionState.copy(searchQuery = event.query))

                }
                viewModelScope.launch {
                    SearchQueryChangesFlow.emit(event.query)
                }
            }


            MainContract.Event.PointSelectionEvent.OnMapSelectionSelected -> {
                setState {
                    copy(
                        showPointSelectBottomSheet = false,
                        isPointPickOnMap = true
                    )
                }

            }

            is MainContract.Event.PointSelectionEvent.OnSearchSelectionSelected -> setState {
                if (event.pointType == PointType.START) {
                    copy(pointSelectionState = pointSelectionState.copy(startPoint = event.point))
                } else copy(pointSelectionState = pointSelectionState.copy(endPoint = event.point))
            }

            is MainContract.Event.PointSelectionEvent.Search -> search(event.query)
            is MainContract.Event.OnSearchResult -> setState {
                copy(pointSelectionState = pointSelectionState.copy(searchResults = event.result))
            }

            is MainContract.Event.OnSelectPointButtonClicked -> {
                setState {
                    copy(
                        pointSelectionState = pointSelectionState.copy(selectedPoint = event.pointType),
                        showPointSelectBottomSheet = true
                    )
                }
                setEffect { MainContract.Effect.PartiallyExpandBottomSheet }
            }

            MainContract.Event.OnDismissPointSelectBottomSheet -> {
                setState {
                    copy(showPointSelectBottomSheet = false)
                }
                setEffect { MainContract.Effect.ExpandBottomSheet }
            }

            is MainContract.Event.OnMapObjectClicked -> {
                setPointByRenderedObject(event.renderedObject)
            }

            MainContract.Event.OnDismissPointDetailsBottomSheet -> setState {
                copy(
                    showDetailsBottomSheet = false
                )
            }

        }


    }

    fun search(query: String) {

        if (query.isEmpty()) return

        val searchQuery = SearchQueryBuilder
            .fromQueryText(query)
            .setPageSize(10)
            .setGeoPoint(GeoPoint(47.237422, 39.712262)) // центр поиска
            .setRadius(Meter(5000f))
            .build()

        searchManager.search(searchQuery).onResult { searchResult ->
            val firstPage = searchResult.firstPage
            val items = firstPage?.items ?: emptyList()
            setEvent(MainContract.Event.OnSearchResult(items))
        }

    }

    fun setPointByRenderedObject(renderedObject: RenderedObject) {
        val source = renderedObject.source as DgisSource
        val id = (renderedObject.item as DgisMapObject).id

        searchManager.searchByDirectoryObjectId(id)
            .onResult onDirectoryObjectReady@{
                val obj = it ?: return@onDirectoryObjectReady

                if (viewState.value.isPointPickOnMap) {
                    setState {
                        if (pointSelectionState.selectedPoint == PointType.START) {
                            copy(pointSelectionState = pointSelectionState.copy(startPoint = it))
                        } else copy(pointSelectionState = pointSelectionState.copy(endPoint = it))
                    }
                    setEffect { MainContract.Effect.ExpandBottomSheet }

                } else {
                    setState { copy(selectedPoint = obj, showDetailsBottomSheet = true) }
                    setEffect { MainContract.Effect.PartiallyExpandBottomSheet }

                }

                val entrancesIds = obj.entrances.map { entranceInfo ->
                    entranceInfo.id
                } as MutableList<DgisObjectId>
                entrancesIds.add(id)

                source.setHighlighted(source.highlightedObjects, false)
                source.setHighlighted(entrancesIds, true)
            }
    }

}



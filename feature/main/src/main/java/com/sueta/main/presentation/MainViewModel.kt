package com.sueta.main.presentation

import androidx.lifecycle.viewModelScope
import com.sueta.core.mLog
import com.sueta.core.presentation.BaseViewModel
import com.sueta.core.presentation.CoroutinesErrorHandler
import com.sueta.main.data.mapper.toGeoPoint
import com.sueta.main.data.mapper.toMarkers
import com.sueta.main.data.mapper.toRoute
import com.sueta.main.data.mapper.toRoutes
import com.sueta.main.domain.repository.MainRepository
import com.sueta.main.presentation.model.PointType
import com.sueta.main.presentation.model.Route
import com.sueta.main.presentation.model.TransportType
import com.sueta.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.dgis.sdk.DGis
import ru.dgis.sdk.DgisObjectId
import ru.dgis.sdk.Meter
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.directory.SearchManager
import ru.dgis.sdk.directory.SearchQueryBuilder
import ru.dgis.sdk.map.DgisMapObject
import ru.dgis.sdk.map.DgisSource
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.RenderedObject
import ru.dgis.sdk.map.RouteEditorSource
import ru.dgis.sdk.routing.BicycleRouteSearchOptions
import ru.dgis.sdk.routing.CarRouteSearchOptions
import ru.dgis.sdk.routing.PedestrianRouteSearchOptions
import ru.dgis.sdk.routing.RouteEditor
import ru.dgis.sdk.routing.RouteEditorRouteParams
import ru.dgis.sdk.routing.RouteSearchOptions
import ru.dgis.sdk.routing.RouteSearchPoint
import ru.dgis.sdk.routing.RouteSearchType
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val searchManager: SearchManager,
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {


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
            MainContract.Event.OnProfileButtonCLicked -> setEffect { MainContract.Effect.Navigation.toSelfProfile }

            is MainContract.Event.OnBudgetClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedBudget = event.budgetType
                    )
                )
            }

            is MainContract.Event.OnTravelStyleClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedStyle = event.travelStyle
                    )
                )

            }

            is MainContract.Event.OnRouteTypeClicked -> setState {
                copy(
                    bottomSheetState = bottomSheetState.copy(
                        selectedRouteType = event.routeType
                    )
                )

            }

            is MainContract.Event.RouteEvent.OnTransportTypeClicked -> {
                setState {
                    copy(
                        routeBottomSheetState = routeBottomSheetState.copy(
                            selectedTransportType = event.transportType
                        )
                    )
                }
                getRoute()
            }


            MainContract.Event.OnGoButtonCLicked -> {
                if (viewState.value.pointSelectionState.startPoint != null && viewState.value.pointSelectionState.endPoint != null) {
                    getRoute()
//                    setState { copy(routeBottomSheetState = routeBottomSheetState.copy()) }
                } else {
                    setEffect { MainContract.Effect.PointsNotSelected }
                }
            }

            MainContract.Event.OnHistoryButtonCLicked -> {
                setState {
                    copy(showHistoryBottomSheet = true)

                }
                getRoutes()
                setEffect { MainContract.Effect.PartiallyExpandBottomSheet }
            }

            MainContract.Event.OnDismissHistoryBottomSheet -> setState {
                copy(showHistoryBottomSheet = false)
            }

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
                        showPointSelectBottomSheet = false, isPointPickOnMap = true
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

            MainContract.Event.RouteEvent.OnDismissPlaceDetailsBottomSheet -> setState {
                copy(
                    routeBottomSheetState = routeBottomSheetState.copy(selectedPlace = null),
                    showPlaceDetailsBottomSheet = false
                )
            }

            is MainContract.Event.RouteEvent.OnPlaceItemClicked -> setState {
                copy(
                    routeBottomSheetState = routeBottomSheetState.copy(selectedPlace = event.place),
                    showPlaceDetailsBottomSheet = true
                )
            }

            MainContract.Event.RouteEvent.OnRouteBottomSheetBackPressed -> setState {
                copy(
                    routeBottomSheetState = routeBottomSheetState.copy(
                        selectedPlace = null, selectedEvent = null, route = null
                    ),
                )
            }

            is MainContract.Event.HistoryEvent.ChangeHistoryTypeButtonClicked -> {
                setState {
                    copy(
                        historyBottomSheetState = historyBottomSheetState.copy(isTopTen = !event.select),
                        error = null
                    )
                }
                getRoutes()
            }

            is MainContract.Event.HistoryEvent.OnHistoryItemClicked -> {
                setRoute(event.route)
                setState { copy(showHistoryBottomSheet = false) }
                setEffect { MainContract.Effect.ExpandBottomSheet }
            }
        }


    }

    private fun search(query: String) {

        if (query.isEmpty()) return

        val searchQuery = SearchQueryBuilder.fromQueryText(query).setPageSize(10)
            .setGeoPoint(GeoPoint(47.237422, 39.712262)).setRadius(Meter(5000f)).build()

        searchManager.search(searchQuery).onResult { searchResult ->
            val firstPage = searchResult.firstPage
            val items = firstPage?.items ?: emptyList()
            setEvent(MainContract.Event.OnSearchResult(items))
        }

    }

    private fun setPointByRenderedObject(renderedObject: RenderedObject) {
        val source = renderedObject.source as DgisSource
        val id = (renderedObject.item as DgisMapObject).id

        searchManager.searchByDirectoryObjectId(id).onResult onDirectoryObjectReady@{
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

    private fun getRoute() {
        baseRequest(errorHandler = object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
            }
        }, request = {
            repository.createRoute(
                startPoint = viewState.value.pointSelectionState.startPoint?.toGeoPoint()
                    ?: GeoPoint(0.0, 0.0),
                endPoint = viewState.value.pointSelectionState.endPoint?.toGeoPoint() ?: GeoPoint(
                    0.0,
                    0.0
                ),
                style = viewState.value.bottomSheetState.selectedStyle,
                length = viewState.value.bottomSheetState.selectedRouteType,
                budget = viewState.value.bottomSheetState.selectedBudget
            )
        }, onSuccess = { response ->
            when (response) {
                is ApiResponse.Success -> {
                    setState {
                        copy(
                            routeBottomSheetState = routeBottomSheetState.copy(route = response.data.toRoute()),
                            isLoading = false
                        )
                    }
                    setRouteOnMap()
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                }

                is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                is ApiResponse.Loading -> setState {
                    copy(
                        isLoading = true,
                        error = null,
                    )
                }
            }
        })
    }

    private fun setRoute(route: Route) {
        setState {
            copy(
                routeBottomSheetState = routeBottomSheetState.copy(route = route),
                isLoading = false
            )
        }
        setRouteOnMap()
    }

    private fun setRouteOnMap() {
        viewModelScope.launch {
            viewState.value.map.collect {
                it?.let { map ->
                    val sdkContext = DGis.context()
                    val routeEditor = RouteEditor(sdkContext)
                    val routeEditorSource = RouteEditorSource(sdkContext, routeEditor)
                    if (routeEditorSource in it.sources) {
                        it.removeSource(routeEditorSource)
                    }
                    it.addSource(routeEditorSource)

                    val pedestrianOptions = RouteSearchOptions(
                        pedestrian = PedestrianRouteSearchOptions(
                            avoidStairways = true,
                            avoidUnderpassesAndOverpasses = true,
                            useIndoor = false,
                        )
                    )
                    val bikeOptions = RouteSearchOptions(
                        bicycle = BicycleRouteSearchOptions(
                            avoidCarRoads = true,
                            avoidStairways = true,
                            avoidUnderpassesAndOverpasses = true,
                        )
                    )

                    val carOptions = RouteSearchOptions(
                        car = CarRouteSearchOptions(
                            avoidTollRoads = true,
                            avoidUnpavedRoads = true,
                            avoidFerries = true,
                            avoidLockedRoads = true,
                            routeSearchType = RouteSearchType.JAM,
                        )
                    )

                    val options =
                        when (viewState.value.routeBottomSheetState.selectedTransportType) {
                            TransportType.WALKING -> pedestrianOptions
                            TransportType.CAR -> carOptions
                            TransportType.BICYCLE -> bikeOptions
                        }

                    val startPoint = RouteSearchPoint(
                        coordinates = viewState.value.pointSelectionState.startPoint?.toGeoPoint()
                            ?: GeoPoint(0.0, 0.0)
                    )

                    val finishPoint = RouteSearchPoint(
                        coordinates = viewState.value.pointSelectionState.endPoint?.toGeoPoint()
                            ?: GeoPoint(0.0, 0.0)
                    )


                    val mapObjectManager = MapObjectManager(map)
                    viewState.value.routeBottomSheetState.route?.places?.toMarkers(sdkContext)
                        ?.forEach {
                            mapObjectManager.addObject(it)

                        }


                    routeEditor.setRouteParams(
                        RouteEditorRouteParams(
                            startPoint = startPoint,
                            finishPoint = finishPoint,
                            routeSearchOptions = options,
                            intermediatePoints = emptyList()
                        )
                    )


                }

            }
        }


    }

    private fun getRoutes() {
        baseRequest(errorHandler = object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                setState { copy(error = message) }
//                    setEffect { CurrencyEffect.ShowError(message) }
            }
        }, request = {
            if (viewState.value.historyBottomSheetState.isTopTen) {
                repository.getTopTenRoutes()
            } else {
                repository.getUserRoutes()
            }
        }, onSuccess = { response ->
            when (response) {
                is ApiResponse.Success -> {
                    setState {
                        copy(
                            historyBottomSheetState = historyBottomSheetState.copy(routes = response.data.toRoutes()),
                        )
                    }
//                        setEffect { ProfileContract.Effect.ProfileWasLoaded }
                }

                is ApiResponse.Failure -> setState { copy(error = response.errorMessage) }
                is ApiResponse.Loading -> setState {
                    copy(
                        isLoading = true,
                        error = null,
                    )
                }
            }
        })
    }

}



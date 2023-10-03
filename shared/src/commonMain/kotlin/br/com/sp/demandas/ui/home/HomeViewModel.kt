package br.com.sp.demandas.ui.home

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import cafe.adriel.voyager.core.screen.Screen

class HomeViewModel :
    BaseViewModel<HomeScreenContract.Event, HomeScreenContract.State, HomeScreenContract.Effect>() {
    override fun createInitialState(): HomeScreenContract.State =
        HomeScreenContract.State(state = ResourceUiState.Idle)

    override fun handleEvent(event: HomeScreenContract.Event) {
        when (event) {
            is HomeScreenContract.Event.GoTo -> setEffect { HomeScreenContract.Effect.GoTo(event.screen) }
        }
    }
}


interface HomeScreenContract {
    sealed interface Event : UiEvent {
        data class GoTo(val screen: Screen) : Event
    }

    data class State(
        val state: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data class GoTo(val screen: Screen) : Effect
    }
}
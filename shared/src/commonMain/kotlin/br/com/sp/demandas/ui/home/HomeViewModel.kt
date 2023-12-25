package br.com.sp.demandas.ui.home

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.mensagem.GetMensagensLocalUseCase
import br.com.sp.demandas.domain.mensagem.GetMensagensUseCase
import br.com.sp.demandas.domain.mensagem.Mensagem
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch

class HomeViewModel(private val getMensagensUseCase: GetMensagensLocalUseCase) :
    BaseViewModel<HomeScreenContract.Event, HomeScreenContract.State, HomeScreenContract.Effect>() {

    init {
            getMensagens()
    }
    override fun createInitialState(): HomeScreenContract.State =
        HomeScreenContract.State(state = ResourceUiState.Idle)
    override fun handleEvent(event: HomeScreenContract.Event) {
        when (event) {
            is HomeScreenContract.Event.GoTo -> setEffect { HomeScreenContract.Effect.GoTo(event.screen) }
        }
    }

    private fun getMensagens() {
        coroutineScope.launch {
            setState {
                copy(
                    state = ResourceUiState.Loading
                )
            }
            getMensagensUseCase.invoke(Unit).onFailure {
                println(it)
                setEffect { HomeScreenContract.Effect.ShowSnackbar(it) }
            }.onSuccess {
                setState {
                    copy(
                        state = ResourceUiState.Success(it),
                    )
                }
            }
        }

    }
}


interface HomeScreenContract {
    sealed interface Event : UiEvent {
        data class GoTo(val screen: Screen) : Event
    }

    data class State(
        val state: ResourceUiState<List<Mensagem>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data class GoTo(val screen: Screen) : Effect
    }
}
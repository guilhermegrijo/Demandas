package br.com.sp.demandas.ui.detalheDemanda

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.GetDemandaDetalheUseCase
import br.com.sp.demandas.ui.demandas.DemandaScreenContract
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class DetalheDemandaViewModel(
    private val getDemandaDetalheUseCase: GetDemandaDetalheUseCase,
    private val demandaId: String
) :
    BaseViewModel<DetalheDemandaContract.Event, DetalheDemandaContract.State, DetalheDemandaContract.Effect>() {


    init {
        coroutineScope.launch {
            getDemandaDetalheUseCase.invoke(demandaId).onFailure {
                println(it)
                setEffect { DetalheDemandaContract.Effect.ShowSnackbar(it) }
                setState {
                    copy(
                        state = ResourceUiState.Idle,
                    )
                }
            }.onSuccess {
                setState {
                    copy(
                        state = ResourceUiState.Success(it),
                    )
                }
            }
        }
    }


    override fun createInitialState(): DetalheDemandaContract.State =
        DetalheDemandaContract.State(state = ResourceUiState.Loading)

    override fun handleEvent(event: DetalheDemandaContract.Event) {
        when(event){
            is DetalheDemandaContract.Event.NavigateToParcelas -> {
                setEffect { DetalheDemandaContract.Effect.GoToParcelas(event.id) }
            }
        }

    }
}

interface DetalheDemandaContract {
    sealed interface Event : UiEvent {
        data class NavigateToParcelas(val id: String) : Event
    }

    data class State(
        val state: ResourceUiState<DemandaDetalhe>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data class GoToParcelas(val id: String) : Effect
    }
}

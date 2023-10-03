package br.com.sp.demandas.ui.mensagens

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.filtroDemanda.model.DemandaState
import br.com.sp.demandas.domain.mensagem.GetMensagensUseCase
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.demandas.DemandaScreenContract
import br.com.sp.demandas.ui.home.HomeScreenContract
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class MensagemViewModel(private val getMensagensUseCase: GetMensagensUseCase) :
    BaseViewModel<MensagemScreenContract.Event, MensagemScreenContract.State, MensagemScreenContract.Effect>() {
    override fun createInitialState(): MensagemScreenContract.State =
        MensagemScreenContract.State(state = ResourceUiState.Idle)

    init {
        getDemandas()
    }

    override fun handleEvent(event: MensagemScreenContract.Event) {
        when (event) {
            is MensagemScreenContract.Event.GoTo -> setEffect { MensagemScreenContract.Effect.GoTo(event.screen) }
        }
    }

    private fun getDemandas() {
        coroutineScope.launch {
            setState {
                copy(
                    state = ResourceUiState.Loading
                )
            }
            getMensagensUseCase.invoke(Unit).onFailure {
                println(it)
                setEffect { MensagemScreenContract.Effect.ShowSnackbar(it) }
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

interface MensagemScreenContract {
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
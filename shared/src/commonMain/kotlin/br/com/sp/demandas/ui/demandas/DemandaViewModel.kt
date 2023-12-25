package br.com.sp.demandas.ui.demandas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.filtroDemanda.FiltroDemandasUseCase
import br.com.sp.demandas.domain.filtroDemanda.GetDemandasFiltroUseCase
import br.com.sp.demandas.domain.filtroDemanda.model.DemandaState
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import br.com.sp.demandas.domain.filtroDemanda.model.FiltroState
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class DemandaViewModel(
    private val getDemandasUseCase: GetDemandasFiltroUseCase,
    private val filtroDemandasUseCase: FiltroDemandasUseCase
) :
    BaseViewModel<DemandaScreenContract.Event, DemandaScreenContract.State, DemandaScreenContract.Effect>() {


    var hasOpen by mutableStateOf(false)


    init {
        val filtro = DemandaState()
        getFiltro(filtro)
    }

    override fun createInitialState(): DemandaScreenContract.State =
        DemandaScreenContract.State(
            state = ResourceUiState.Loading,
            filtroState = FiltroState()
        )

    override fun handleEvent(event: DemandaScreenContract.Event) {
        when (event) {
            is DemandaScreenContract.Event.FiltroRegional -> {
                var newState = (uiState.value.state as ResourceUiState.Success).data
                if (event.filtro.id != newState.filtroInput?.regional?.id) {
                    newState = newState.copy(
                        filtroState = newState.filtroState?.copy(
                            prefeituraFiltro = "",
                            filtroPrefeituras = emptyList()
                        ),
                        filtroInput = newState.filtroInput?.copy(
                            prefeitura = null,
                            idAtividadeEtapa = null,
                            idConvenio = null,
                            idAtividadeStatus = null,
                            alerta = null,
                            alarme = null,
                            noPrazo = null,
                            aviso = null
                        )
                    )
                }

                newState = newState.copy(
                    filtroInput = newState.filtroInput?.copy(regional = if (event.filtro.id == "") null else event.filtro),
                    filtroState = newState.filtroState?.copy(regionalFiltro = event.filtro.texto)
                )

                setState {
                    copy(
                        state = ResourceUiState.Loading,
                        filtroState = newState.filtroState!!
                    )
                }

                getFiltro(newState)
            }

            is DemandaScreenContract.Event.FiltroPrefeitura -> {
                var newState = (uiState.value.state as ResourceUiState.Success).data

                newState = if (event.filtro.texto == "")
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(
                            prefeitura = null,
                            idAtividadeStatus = null,
                            idConvenio = null,
                            idAtividadeEtapa = null
                        ),
                        filtroState = newState.filtroState?.copy(
                            prefeituraFiltro = "",
                            statusFiltro = "",
                            etapaFiltro = "",
                            convenioFiltro = ""
                        )
                    )
                else
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(prefeitura = event.filtro),
                        filtroState = newState.filtroState?.copy(prefeituraFiltro = event.filtro.texto)
                    )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }
            }

            DemandaScreenContract.Event.Refresh -> {}
            is DemandaScreenContract.Event.FiltroConvenio -> {
                var newState = (uiState.value.state as ResourceUiState.Success).data
                newState = if (event.filtro.texto == "")
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(idConvenio = null),
                        filtroState = newState.filtroState?.copy(convenioFiltro = "")
                    )
                else
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(idConvenio = event.filtro),
                        filtroState = newState.filtroState?.copy(convenioFiltro = event.filtro.texto)
                    )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }


            }

            is DemandaScreenContract.Event.FiltroEtapa -> {

                var newState = (uiState.value.state as ResourceUiState.Success).data
                newState = if (event.filtro.texto == "")
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(idAtividadeEtapa = null),
                        filtroState = newState.filtroState?.copy(etapaFiltro = "")
                    )
                else
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(idAtividadeEtapa = event.filtro),
                        filtroState = newState.filtroState?.copy(etapaFiltro = event.filtro.texto)
                    )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }
            }

            is DemandaScreenContract.Event.FiltroNumeroDemanda -> {

                var newState = (uiState.value.state as ResourceUiState.Success).data
                newState = if (event.numero == "")
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(numeroDemanda = null),
                        filtroState = newState.filtroState?.copy(numeroDemandaFiltro = "")
                    )
                else
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(numeroDemanda = event.numero),
                        filtroState = newState.filtroState?.copy(numeroDemandaFiltro = event.numero)
                    )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }
            }


            is DemandaScreenContract.Event.FiltroStatus -> {
                var newState = (uiState.value.state as ResourceUiState.Success).data
                newState = if (event.filtro.texto == "")
                    newState.copy(
                        filtroInput = newState.filtroInput?.copy(idAtividadeStatus = null),
                        filtroState = newState.filtroState?.copy(statusFiltro = "")
                    )
                else newState.copy(
                    filtroInput = newState.filtroInput?.copy(idAtividadeStatus = event.filtro),
                    filtroState = newState.filtroState?.copy(statusFiltro = event.filtro.texto)
                )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }
            }

            is DemandaScreenContract.Event.FiltroSelect -> {

                var newState = (uiState.value.state as ResourceUiState.Success).data
                newState = newState.copy(
                    filtroInput = newState.filtroInput?.copy(
                        alarme = event.list.contains("Prazo vencido (alerta e alarme)"),
                        aviso = event.list.contains("No prazo (com aviso)"),
                        alerta = event.list.contains("Prazo vencido (alerta e alarme)"),
                        noPrazo = event.list.contains("No prazo (sem aviso)")
                    ),
                    filtroState = newState.filtroState?.copy(filtroSelect = event.list)
                )
                setState {
                    copy(
                        state = ResourceUiState.Success(newState),
                        filtroState = newState.filtroState!!
                    )
                }
            }

            is DemandaScreenContract.Event.FiltrarDemanda -> {
                var newState = (uiState.value.state as ResourceUiState.Success).data
                getDemanda(newState)
            }

            is DemandaScreenContract.Event.NavigateToDetail -> {
                setEffect { DemandaScreenContract.Effect.NavigateToDetail(event.id) }
            }
        }
    }

    private fun getFiltro(demandaFIltroInput: DemandaState) {
        screenModelScope.launch {
            setState {
                copy(
                    state = ResourceUiState.Loading
                )
            }
            getDemandasUseCase.invoke(demandaFIltroInput).onFailure {
                println(it)
                setEffect { DemandaScreenContract.Effect.ShowSnackbar(it) }
                setState {
                    copy(
                        state = ResourceUiState.Success(DemandaState())
                    )
                }
            }.onSuccess {
                    setState {
                        copy(
                            state = ResourceUiState.Success(it),
                            filtroState = it.filtroState!!
                        )
                    }
            }
        }

    }

    private fun getDemanda(demandaFIltroInput: DemandaState) {
        screenModelScope.launch {
            setState {
                copy(
                    state = ResourceUiState.Loading
                )
            }
            filtroDemandasUseCase.invoke(demandaFIltroInput).onFailure {
                println(it)
                setEffect { DemandaScreenContract.Effect.ShowSnackbar(it) }
                setState {
                    copy(
                        state = ResourceUiState.Success(DemandaState())
                    )
                }
            }.onSuccess {

                if (it.filtroState?.regionalFiltro?.isEmpty() == true && it.lista?.size == 1) {
                    setEffect { DemandaScreenContract.Effect.NavigateToDetail(it.lista.first().idAtividade) }
                    setState {
                        copy(
                            state = ResourceUiState.Success(it), filtroState = it.filtroState!!
                        )
                    }
                } else
                    setState {
                        copy(
                            state = ResourceUiState.Success(it),
                            filtroState = it.filtroState!!
                        )
                    }
            }
        }

    }
}

interface DemandaScreenContract {
    sealed interface Event : UiEvent {
        data object Refresh : Event
        data object FiltrarDemanda : Event
        data class FiltroRegional(val filtro: Filtro) : Event
        data class FiltroPrefeitura(val filtro: Filtro) : Event
        data class FiltroConvenio(val filtro: Filtro) : Event
        data class FiltroEtapa(val filtro: Filtro) : Event
        data class FiltroNumeroDemanda(val numero: String) : Event
        data class FiltroStatus(val filtro: Filtro) : Event
        data class FiltroSelect(val list: List<String>) : Event
        data class NavigateToDetail(val id: String) : Event
    }

    data class State(
        val state: ResourceUiState<DemandaState>,
        val filtroState: FiltroState
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetail(val id: String) : Effect
        data class ShowSnackbar(val throwable: Throwable) : Effect
    }
}

//No prazo, aviso, alerta e alarme
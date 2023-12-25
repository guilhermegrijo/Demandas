package br.com.sp.demandas.ui.login.checkCode

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.auth.CheckCodeUseCase
import br.com.sp.demandas.domain.auth.model.CheckCodeModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class CheckCodeViewModel(private val checkCodeUseCase: CheckCodeUseCase, private val user: String) :
    BaseViewModel<CheckCodeContract.Event, CheckCodeContract.State, CheckCodeContract.Effect>() {
    override fun createInitialState(): CheckCodeContract.State =
        CheckCodeContract.State(state = ResourceUiState.Idle)

    override fun handleEvent(event: CheckCodeContract.Event) {
        when (event) {
            is CheckCodeContract.Event.checkCode -> {
                checkCode(event.code)
            }
        }
    }

    private fun checkCode(code: String) {
        setState { copy(state = ResourceUiState.Loading) }

        coroutineScope.launch {
            checkCodeUseCase.invoke(CheckCodeModel(user, code))
                .onFailure {
                    setEffect { CheckCodeContract.Effect.ShowSnackbar(it) }
                    setState { copy(state = ResourceUiState.Error(it)) }
                }.onSuccess {
                    setEffect { CheckCodeContract.Effect.NavigateToNewPassword(user, it.mensagemRetorno ?: "") }
                }
        }
    }
}

interface CheckCodeContract {
    sealed interface Event : UiEvent {
        data class checkCode(val code: String) : Event
    }

    data class State(
        val state: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data class NavigateToNewPassword(val user: String, val code : String) : Effect
    }
}
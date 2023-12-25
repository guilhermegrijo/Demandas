package br.com.sp.demandas.ui.login.changedPassword

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.auth.CheckCodeUseCase
import br.com.sp.demandas.domain.auth.model.CheckCodeModel
import br.com.sp.demandas.ui.login.checkCode.CheckCodeContract
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class ChangedPasswordViewModel () :
    BaseViewModel<ChangedPasswordContract.Event, ChangedPasswordContract.State, ChangedPasswordContract.Effect>() {
    override fun createInitialState(): ChangedPasswordContract.State =
        ChangedPasswordContract.State(state = ResourceUiState.Idle)

    override fun handleEvent(event: ChangedPasswordContract.Event) {
        when (event) {
            is ChangedPasswordContract.Event.GoToLogin -> {
                setEffect { ChangedPasswordContract.Effect.NavigateToLogin }
            }
        }
    }

    private fun checkCode(code: String) {
        setState { copy(state = ResourceUiState.Loading) }
    }
}

interface ChangedPasswordContract {
    sealed interface Event : UiEvent {
        data object GoToLogin : Event
    }

    data class State(
        val state: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data object NavigateToLogin : Effect
    }
}
package br.com.sp.demandas.ui.login.firstAccess

import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState

class FirstAccessViewModel() :
    BaseViewModel<FirstAccessContract.Event, FirstAccessContract.State, FirstAccessContract.Effect>() {
    override fun createInitialState(): FirstAccessContract.State =
        FirstAccessContract.State(state = ResourceUiState.Idle)

    override fun handleEvent(event: FirstAccessContract.Event) {
        when (event) {
            is FirstAccessContract.Event.GoToForgotPassword -> {
                setEffect {
                    FirstAccessContract.Effect.NavigateToForgotPassword
                }
            }

            FirstAccessContract.Event.GoToHome -> setEffect {
                FirstAccessContract.Effect.NavigateToHome
            }
        }
    }
}

interface FirstAccessContract {
    sealed interface Event : UiEvent {
        data object GoToForgotPassword : Event
        data object GoToHome : Event
    }

    data class State(
        val state: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data object NavigateToHome : Effect
        data object NavigateToForgotPassword : Effect
    }
}
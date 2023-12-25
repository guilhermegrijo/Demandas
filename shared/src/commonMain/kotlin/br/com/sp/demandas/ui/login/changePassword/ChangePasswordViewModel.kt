package br.com.sp.demandas.ui.login.changePassword

import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.auth.ChangePasswordUseCase
import br.com.sp.demandas.domain.auth.model.TrocarSenhaModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val platform: Platform,
    private val user: String,
    private val code : String
) :
    BaseViewModel<ChangePasswordContract.Event, ChangePasswordContract.State, ChangePasswordContract.Effect>() {
    override fun createInitialState(): ChangePasswordContract.State =
        ChangePasswordContract.State(stateForgotPassword = ResourceUiState.Idle)

    override fun handleEvent(event: ChangePasswordContract.Event) {
        when (event) {
            is ChangePasswordContract.Event.ChangePassword -> {
                forgotPassword(event.password)
            }

            else -> {}
        }
    }

    private fun forgotPassword(password: String) {
        setState { copy(stateForgotPassword = ResourceUiState.Loading) }



        coroutineScope.launch {
            changePasswordUseCase.invoke(TrocarSenhaModel(user, code, password))
                .onFailure {
                    setEffect { ChangePasswordContract.Effect.ShowSnackbar(it) }
                    setState {
                        ChangePasswordContract.State(
                            stateForgotPassword = ResourceUiState.Error(
                                it
                            )
                        )
                    }
                }.onSuccess {
                    setEffect { ChangePasswordContract.Effect.NavigateToPasswordChanged }
                }
        }
    }
}


interface ChangePasswordContract {
    sealed interface Event : UiEvent {
        data class ChangePassword(val password: String) : Event
    }

    data class State(
        val stateForgotPassword: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToPasswordChanged : Effect
        data class ShowSnackbar(val throwable: Throwable) : Effect
    }
}

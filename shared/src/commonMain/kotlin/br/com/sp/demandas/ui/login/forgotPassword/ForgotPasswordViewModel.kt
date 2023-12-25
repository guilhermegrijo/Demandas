import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.auth.ForgotPasswordUseCase
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val forgotPasswordUseCase: ForgotPasswordUseCase) :
    BaseViewModel<ForgotScreenContract.Event, ForgotScreenContract.State, ForgotScreenContract.Effect>() {
    override fun createInitialState(): ForgotScreenContract.State =
        ForgotScreenContract.State(stateForgotPassword = ResourceUiState.Idle)

    override fun handleEvent(event: ForgotScreenContract.Event) {
        when (event) {
            is ForgotScreenContract.Event.SendEmail -> {
                forgotPassword(event.user)
            }

            else -> {}
        }
    }

    private fun forgotPassword(user: String) {

        if(user.isEmpty()){
            setEffect { ForgotScreenContract.Effect.ShowSnackbar(Exception("Por favor insira o e-mail")) }
            setState { ForgotScreenContract.State(stateForgotPassword = ResourceUiState.Error(Exception("Por favor insira o e-mail"))) }
            return
        }

        setState { copy(stateForgotPassword = ResourceUiState.Loading) }

        coroutineScope.launch {
            forgotPasswordUseCase.invoke(user).onFailure {
                setEffect { ForgotScreenContract.Effect.ShowSnackbar(it) }
                setState { ForgotScreenContract.State(stateForgotPassword = ResourceUiState.Error(it)) }
            }.onSuccess {
                setEffect { ForgotScreenContract.Effect.NavigateToCheckCode(it) }
            }
        }
    }
}


interface ForgotScreenContract {
    sealed interface Event : UiEvent {
        data class SendEmail(val user: String) : Event
    }

    data class State(
        val stateForgotPassword: ResourceUiState<Unit>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToCheckCode(val user: String) : Effect
        data class ShowSnackbar(val throwable: Throwable) : Effect
    }
}

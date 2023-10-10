package br.com.sp.demandas.ui.login.makeLogin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.core.ui.BaseViewModel
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.UiEffect
import br.com.sp.demandas.core.ui.UiEvent
import br.com.sp.demandas.core.ui.UiState
import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.domain.auth.DoLoginUseCase
import br.com.sp.demandas.domain.auth.RefreshTokenUseCase
import br.com.sp.demandas.domain.auth.UpdateUserUseCase
import br.com.sp.demandas.domain.user.GetUserUseCase
import br.com.sp.demandas.domain.user.User
import cafe.adriel.voyager.core.model.coroutineScope
import dev.icerock.moko.biometry.BiometryAuthenticator
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class LoginViewModel(
    private val doLoginUseCase: DoLoginUseCase,
    val biometryAuthenticator: BiometryAuthenticator,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val platform: Platform
) : BaseViewModel<LoginScreenContract.Event, LoginScreenContract.State, LoginScreenContract.Effect>() {


    val UserState: MutableState<User> =
        mutableStateOf(User("", 0, 0, "", "", "", "", true, false, true))

    val retry = mutableStateOf(0)

    init {
        coroutineScope.launch {
            getUserUseCase.invoke(Unit).onSuccess {
                it?.let {
                    UserState.value = it
                    if (biometryAuthenticator.isBiometricAvailable() && it.loginByBiometry) setState {
                        LoginScreenContract.State(
                            stateLogin = ResourceUiState.Success(
                                LoginState(
                                    it.login, askEnrollBiometry = false, biometricAvaible = false
                                )
                            )
                        )
                    }
                    else setState {
                        LoginScreenContract.State(
                            stateLogin = ResourceUiState.Success(
                                LoginState(
                                    it.login, askEnrollBiometry = false, biometricAvaible = false
                                )
                            )
                        )
                    }
                }

            }
        }
    }

    private fun doLogin(user: String, password: String) {
        coroutineScope.launch {
            doLoginUseCase.invoke(LoginModel(user, password, true)).onFailure {
                setState { copy(stateLogin = ResourceUiState.Idle) }
                setEffect { LoginScreenContract.Effect.ShowSnackbar(it) }
                setState { copy(stateLogin = ResourceUiState.Error(it)) }
            }.onSuccess {
                setState { copy(stateLogin = ResourceUiState.Idle) }
                updateUserUseCase.invoke(it.copy(flagPrimeiroAcesso = false))
                /*if (biometryAuthenticator.isBiometricAvailable() && UserState.value.flagPrimeiroAcesso) {
                    if (platform.name.contains(
                            "IOS",
                            ignoreCase = true
                        )
                    ) setEffect { LoginScreenContract.Effect.ShowDialog }
                    else askEnrollment()
                } else */setEffect { LoginScreenContract.Effect.NavigateToHome }

            }
        }
    }

    override fun createInitialState(): LoginScreenContract.State {
        return LoginScreenContract.State(stateLogin = ResourceUiState.Idle)
    }

    override fun handleEvent(event: LoginScreenContract.Event) {
        when (event) {
            is LoginScreenContract.Event.DoLogin -> {
                setState { copy(stateLogin = ResourceUiState.Loading) }
                coroutineScope.launch {
                    UserState.value.let {
                        /*if (biometryAuthenticator.isBiometricAvailable() && it.loginByBiometry)
                            try {
                                val isSuccess =
                                    biometryAuthenticator.checkBiometryAuthentication(
                                        requestTitle = "Acesso por biometria".desc(),
                                        requestReason = " ".desc(),
                                        failureButtonText = "Oops".desc()
                                    )
                                if (isSuccess) {
                                    refreshTokenUseCase.invoke(
                                        Unit
                                    ).onSuccess {

                                        setState { copy(stateLogin = ResourceUiState.Idle) }
                                        setEffect { LoginScreenContract.Effect.NavigateToHome }
                                    }.onFailure {

                                        setState { copy(stateLogin = ResourceUiState.Idle) }
                                        setEffect { LoginScreenContract.Effect.ShowSnackbar(it) }
                                        setState { copy(stateLogin = ResourceUiState.Error(it)) }
                                    }
                                } else {
                                    setState { copy(stateLogin = ResourceUiState.Idle) }
                                    retry.value += 1
                                    if (retry.value > 1) {
                                        setEffect {
                                            LoginScreenContract.Effect.ShowSnackbar(
                                                Throwable("Número de tentativas permitidas excedido,acesso por biometria desativado")
                                            )
                                        }
                                        UserState.value =
                                            UserState.value.copy(loginByBiometry = false)
                                    }
                                }
                            } catch (throwable: Throwable) {
                                println(throwable)
                                setEffect { LoginScreenContract.Effect.ShowSnackbar(throwable) }
                                setState { copy(stateLogin = ResourceUiState.Error(throwable)) }
                            }
                        else */doLogin(event.user, event.password)
                    }
                }
            }

            LoginScreenContract.Event.BiometricAuthRefused -> biometricAuthRefused()
        }
    }

    private fun biometricAuthRefused() {
        setEffect { LoginScreenContract.Effect.NavigateToHome }
    }

    fun askEnrollment() {
        coroutineScope.launch {
            try {
                val isSuccess = biometryAuthenticator.checkBiometryAuthentication(
                    requestTitle = "O app tem suporte a biometria".desc(),
                    requestReason = "Uma vez ativado, você pode acessar esse login usando apenas sua biometria".desc(),
                    failureButtonText = "Oops".desc()
                )
                if (isSuccess) {
                    setState { copy(stateLogin = ResourceUiState.Idle) }
                    getUserUseCase.invoke(Unit).onSuccess {
                        it?.let {
                            updateUserUseCase.invoke(
                                it.copy(
                                    loginByBiometry = true,
                                    flagPrimeiroAcesso = false
                                )
                            ).onSuccess {
                                setEffect { LoginScreenContract.Effect.NavigateToHome }
                            }.onFailure {
                                setEffect { LoginScreenContract.Effect.ShowSnackbar(it) }
                                setState { copy(stateLogin = ResourceUiState.Error(it)) }
                            }
                        }

                    }
                } else {
                    setEffect { LoginScreenContract.Effect.NavigateToHome }
                }
            } catch (throwable: Throwable) {
                println(throwable)
                setEffect { LoginScreenContract.Effect.ShowSnackbar(throwable) }
                setState { copy(stateLogin = ResourceUiState.Error(throwable)) }
            }
        }

    }

}

interface LoginScreenContract {
    sealed interface Event : UiEvent {
        data class DoLogin(val user: String, val password: String) : Event
        data object BiometricAuthRefused : Event
    }

    data class State(
        val stateLogin: ResourceUiState<LoginState>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToHome : Effect
        data class ShowSnackbar(val throwable: Throwable) : Effect
        data object ShowDialog : Effect
    }
}

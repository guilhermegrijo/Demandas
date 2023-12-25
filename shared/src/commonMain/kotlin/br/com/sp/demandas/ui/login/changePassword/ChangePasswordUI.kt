package br.com.sp.demandas.ui.login.changePassword

import EsqueceuSenhaTitleAndSubTitle
import ForgotPasswordViewModel
import ForgotScreenContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.ui.login.changedPassword.ChangedPasswordUI
import br.com.sp.demandas.ui.login.checkCode.CheckCodeUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf


class ChangePasswordUI(val user : String, val code : String) : Screen {

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val viewModel = getScreenModel<ChangePasswordViewModel>{ parametersOf(user, code) }
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        viewModel.effect.observeAsActions {
            if (it is ChangePasswordContract.Effect.ShowSnackbar) {
                when (it.throwable) {
                    is ClientException -> {
                        snackbarType.value = (SnackbarType.ALERT)
                        println(snackbarType)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "Erro no App",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }

                    is ServerException -> {

                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ERROR)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }

                    is UnknownException -> {

                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ERROR)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "Erro indefinido",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }
                }
            }
            if (it is ChangePasswordContract.Effect.NavigateToPasswordChanged) {
                navigator.push(ChangedPasswordUI())
            }
        }

        Scaffold(
            topBar = {
                EsqueceuSenhaTopAppBar {
                    navigator.pop()
                }
            },
            snackbarHost = {
                SnackbarHost(scaffoldState.snackbarHostState) {
                    Snackbar(it.actionLabel ?: "", it.message, snackbarType.value) {
                        it.dismiss()
                    }
                }
            }) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(Modifier.height(28.dp))
                EsqueceuSenhaTitleAndSubTitle()
                Spacer(Modifier.height(18.dp))
                NovaSenhaField(viewModel)
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                val logo = if (isSystemInDarkTheme()) MR.images.sp_mini else MR.images.sp_mini

                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(logo),
                    contentDescription = "Localized_Logo"
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NovaSenhaField(viewModel: ChangePasswordViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.uiState.collectAsState()

    val password = rememberSaveable {
        mutableStateOf("")
    }

    val confirmPassword = rememberSaveable {
        mutableStateOf("")
    }

    UserInput(
        title = "Nova senha",
        userValue = password,
        enabled = state.stateForgotPassword !is ResourceUiState.Loading,
        isError = state.stateForgotPassword is ResourceUiState.Error,
        textError = if (state.stateForgotPassword is ResourceUiState.Error) (state.stateForgotPassword as ResourceUiState.Error).throwable?.message
            ?: "" else ""
    )
    Spacer(Modifier.height(8.dp))
    UserInput(
        title = "Confirmar senha",
        userValue = confirmPassword,
        enabled = state.stateForgotPassword !is ResourceUiState.Loading,
        isError = state.stateForgotPassword is ResourceUiState.Error,
        textError = if (state.stateForgotPassword is ResourceUiState.Error) (state.stateForgotPassword as ResourceUiState.Error).throwable?.message
            ?: "" else ""
    )


    Spacer(Modifier.height(35.dp))
    ButtonAction(
        enabled = (password.value == confirmPassword.value) && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty(),
        loading = true,
        onClick = {
            viewModel.setEvent(ChangePasswordContract.Event.ChangePassword( password.value))
            keyboardController?.hide()
        }
    )
}



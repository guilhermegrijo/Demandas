package br.com.sp.demandas.ui.login.forgotPassword

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
import br.com.sp.demandas.design.components.CustomDialog
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.ui.login.checkCode.CheckCodeUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch


class ForgotPasswordUI : Screen {

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val viewModel = getScreenModel<ForgotPasswordViewModel>()
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        viewModel.effect.observeAsActions {
            if (it is ForgotScreenContract.Effect.ShowSnackbar) {
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
                                "Erro no servidor",
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
            if (it is ForgotScreenContract.Effect.NavigateToCheckCode) {
                navigator.push(CheckCodeUI(it.user))
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
                EsqueceuSenhaField(viewModel)
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                val logo = if (isSystemInDarkTheme()) MR.images.govsp else MR.images.govsp

                Image(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    painter = painterResource(logo),
                    contentDescription = "Localized_Logo"
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EsqueceuSenhaField(viewModel: ForgotPasswordViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.uiState.collectAsState()

    val user = rememberSaveable {
        mutableStateOf("")
    }
    UserInput(
        userValue = user,
        enabled = state.stateForgotPassword !is ResourceUiState.Loading,
        isError = state.stateForgotPassword is ResourceUiState.Error,
        textError = if (state.stateForgotPassword is ResourceUiState.Error) (state.stateForgotPassword as ResourceUiState.Error).throwable?.message
            ?: "" else ""
    )
    Spacer(Modifier.height(25.dp))

    Spacer(Modifier.height(35.dp))
    ButtonAction(
        onClick = {
            viewModel.setEvent(ForgotScreenContract.Event.SendEmail(user.value))
            keyboardController?.hide()
        }
    )
}



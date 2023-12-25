package br.com.sp.demandas.ui.login.firstAccess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxButton
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.ui.home.HomeScreen
import br.com.sp.demandas.ui.login.changePassword.EsqueceuSenhaTopAppBar
import br.com.sp.demandas.ui.login.changedPassword.ChangedPasswordContract
import br.com.sp.demandas.ui.login.changedPassword.ChangedPasswordViewModel
import br.com.sp.demandas.ui.login.forgotPassword.ForgotPasswordUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import kotlinx.coroutines.launch

class FirstAccessUI : Screen {

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val viewModel = getScreenModel<FirstAccessViewModel>()
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        viewModel.effect.observeAsActions {
            if (it is FirstAccessContract.Effect.ShowSnackbar) {
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
            if (it is FirstAccessContract.Effect.NavigateToHome) {
                navigator.push(HomeScreen())
            }
            if (it is FirstAccessContract.Effect.NavigateToForgotPassword) {
                navigator.push(ForgotPasswordUI())
            }
        }

        Scaffold(
            topBar = {
                EsqueceuSenhaTopAppBar {
                    navigator.popUntilRoot()
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
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {


                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "É seu primeiro acesso, deseja alterar a senha?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Column {
                    MaxButton(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        text = "Alterar senha",
                        enabled = true,
                        onClick = {
                            viewModel.handleEvent(FirstAccessContract.Event.GoToForgotPassword)
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled = true,
                        onClick = {
                            viewModel.handleEvent(FirstAccessContract.Event.GoToHome)
                        }
                    ) {
                        Text("Ir para tela inicial")
                    }
                }

            }
        }
    }
}
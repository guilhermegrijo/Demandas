package br.com.sp.demandas.ui.login.makeLogin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.sp.demandas.design.components.MaxColumn
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.CustomDialog
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.ui.home.HomeScreen
import br.com.sp.demandas.ui.login.forgotPassword.ForgotPasswordUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.biometry.compose.BindBiometryAuthenticatorEffect
import dev.icerock.moko.biometry.compose.BiometryAuthenticatorFactory
import dev.icerock.moko.biometry.compose.rememberBiometryAuthenticatorFactory
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf


class LoginScreen : Screen {


    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val biometryFactory: BiometryAuthenticatorFactory = rememberBiometryAuthenticatorFactory()

        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val viewModel =
            getScreenModel<LoginViewModel> { parametersOf(biometryFactory.createBiometryAuthenticator()) }
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        BindBiometryAuthenticatorEffect(viewModel.biometryAuthenticator)
        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow



        viewModel.effect.observeAsActions {
            if (it is LoginScreenContract.Effect.ShowSnackbar) {

                when (it.throwable) {
                    is ClientException -> {
                        snackbarType.value = (SnackbarType.ALERT)
                        println(snackbarType)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
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
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
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
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "Erro indefinido",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }

                    else -> {
                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ALERT)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                "Atenção",
                                it.throwable.message.toString(),
                                SnackbarDuration.Indefinite
                            )
                        }
                    }
                }
            }
            if (it is LoginScreenContract.Effect.ShowDialog) {
                isDialogOpen = true
            }
            if (it is LoginScreenContract.Effect.NavigateToHome) {
                navigator.push(HomeScreen())
            }
        }
        Scaffold(
            topBar = {
                MaxTopAppBarCenter(title = {
                    Image(
                        modifier = Modifier.size(240.dp),
                        painter = painterResource(MR.images.govsp),
                        contentDescription = "logo"
                    )

                }, navigationIcon = {}, actions = {})
            },
            snackbarHost = {
                SnackbarHost(scaffoldState.snackbarHostState) {
                    Snackbar(it.actionLabel ?: "", it.message, snackbarType.value) {
                        it.dismiss()
                    }
                }
            }) {
            if (isDialogOpen)
                CustomDialog(
                    "O app tem suporte a biometria",
                    "Uma vez ativado, você pode acessar esse login usando apenas sua biometria. Deseja ativa-lo?",
                    imageResource = painterResource(MR.images.face_square),
                    accepted = {
                        isDialogOpen = false
                        viewModel.askEnrollment()
                    }
                ) {
                    viewModel.handleEvent(LoginScreenContract.Event.BiometricAuthRefused)
                }
            MaxColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),

                ) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Spacer(Modifier.height(16.dp))

                    Column(
                        Modifier.fillMaxWidth().background(Color.LightGray).padding(vertical = 12.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "SGRI - SECRETARIA DE GOVERNO E RELACIONAMENTO INSTITUCIONAL",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                        )
                        androidx.compose.material3.Text(
                            text = "SUBSECRETARIA DE CONVÊNIOS COM MUNICÍPIOS E ENTIDADES NÃO GOVERNAMENTAIS",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            ),
                        )
                    }

                }

                Spacer(Modifier.height(35.dp))
                androidx.compose.material3.Text(
                    text = "Aplicativo Convênios SGRI",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                )
                //LoginTitleAndSubTitle()
                LoginField(viewModel)
                Spacer(Modifier.height(20.dp))
                LoginTextButton {
                    navigator.push(ForgotPasswordUI())
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = "",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                        )
                    )


                }


                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

            }
        }
    }
}
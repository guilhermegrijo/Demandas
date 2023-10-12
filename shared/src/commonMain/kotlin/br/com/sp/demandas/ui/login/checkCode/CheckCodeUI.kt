package br.com.sp.demandas.ui.login.checkCode

import ForgotPasswordViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxButton
import br.com.sp.demandas.design.components.MaxColumn
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.ui.login.forgotPassword.EsqueceuSenhaTopAppBar
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

data class CheckCodeUI(val user: String) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scaffoldState = rememberScaffoldState()
        val viewModel = getScreenModel<CheckCodeViewModel> { parametersOf(user) }
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.uiState.collectAsState()

        var otpValue = remember {
            mutableStateOf("")
        }

        viewModel.effect.observeAsActions {
            if (it is CheckCodeContract.Effect.ShowSnackbar) {
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
            if (it is CheckCodeContract.Effect.NavigateToNewPassword) {
                navigator.push(CheckCodeUI(it.user))
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
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
            MaxColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(Modifier.height(28.dp))
                Text(
                    text = "Código de verificação",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "insira o código recebido para o usuário $user",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(Modifier.height(32.dp))
                OtpField(otpValue = otpValue)
                Spacer(Modifier.height(32.dp))
                MaxButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    text = "Verificar",
                    enabled = state.state !is ResourceUiState.Loading,
                    onClick = {
                        viewModel.setEvent(CheckCodeContract.Event.checkCode(otpValue.value))
                    }
                )
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                val logo = if (isSystemInDarkTheme()) MR.images.sp_mini else MR.images.sp_mini

                Image(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    painter = painterResource(logo),
                    contentDescription = "Localized_Logo"
                )

            }
        }
    }

    @Composable
    fun OtpField(
        otpCount: Int = 5,
        otpValue: MutableState<String>,
    ) {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {


            BasicTextField(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    .focusRequester(focusRequester),
                value = otpValue.value,
                onValueChange = { otpValue.value = it.take(otpCount) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(otpCount) { index ->
                            CharView(
                                index = index,
                                text = otpValue.value
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                },
            )
        }
    }

}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isDarkMode = isSystemInDarkTheme()
    val color =
        if (isDarkMode) Color(0xff1C1A22) else Color(0xffF8F8FA)

    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(56.dp)
            .border(
                1.dp, when {
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> color
                }, RoundedCornerShape(12.dp)
            )
            .padding(2.dp)
            .drawBehind {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(12f, 12f),
                    size = this.size
                )
            },
        text = char,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}
package br.com.sp.demandas.ui.login.makeLogin


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.PasswordCustomField
import br.com.sp.demandas.design.components.UserCustomField


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun LoginField(
    viewModel: LoginViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    val user = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel.UserState.value) {
        user.value = viewModel.UserState.value.login
    }

    val keyboardController = LocalSoftwareKeyboardController.current



    val bringIntoViewRequest = BringIntoViewRequester()

    Column(
        Modifier
            .fillMaxWidth(0.85f)
    ) {
        UserCustomField(
            userValue = user.value,
            enabled = true,
            isError = false,
            textError = "",
            onChange = { user.value = it }
        )


        /*        if (viewModel.state.value.canLoginWithBiometry && user.value == state.userLogin) {
                    Spacer(Modifier.height(12.dp))
                } else*/

        if (viewModel.UserState.value.loginByBiometry && viewModel.UserState.value.login == user.value)
            Spacer(Modifier.height(7.dp))
        else
            PasswordCustomField(
                passwordValue = password.value,
                enabled = true,
                onAction = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                isError = state.stateLogin is ResourceUiState.Error,
                textError = "",
                onChange = { password.value = it }
            )
        /*
    MaxTextButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Esqueceu a senha?",
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colors.primaryVariant,
        align = Arrangement.End,
        enabled = false
    ) {
        viewModel.onEvent(LoginEvent.Navigate(LoginScreen.EsqueceuSenha(fragment)))
    }*/
        Spacer(Modifier.height(12.dp))
        LoginButton(
            enabled = state.stateLogin !is ResourceUiState.Loading,
            onClick = {
                viewModel.setEvent(LoginScreenContract.Event.DoLogin(user.value, password.value))
                keyboardController?.hide()
            }
        )
    }

}
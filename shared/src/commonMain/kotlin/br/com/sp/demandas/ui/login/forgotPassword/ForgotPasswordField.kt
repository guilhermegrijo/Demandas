package br.com.sp.demandas.ui.login.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.components.UserCustomField


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInput(
    userValue: MutableState<String>,
    enabled: Boolean,
    isError: Boolean,
    textError: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Coloque seu usuário ou e-mail",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(Modifier.height(10.dp))
        UserCustomField(
            userValue = userValue.value,
            enabled = enabled,
            isError = isError,
            imeAction = ImeAction.Done,
            onAction = KeyboardActions {
                keyboardController?.hide()
            },
            textError = textError,
            onChange = {userValue.value = it}
        )
    }
}
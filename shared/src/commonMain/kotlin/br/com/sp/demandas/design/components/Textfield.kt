package br.com.sp.demandas.design.components


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import br.com.sp.demandas.design.animation.MaxAnimatedVisibility
import dev.icerock.moko.resources.compose.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    helperText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
    isError: Boolean = false,
    textError: String? = null,
) {

    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            modifier = modifier
                .fillMaxWidth()
                .indicatorLine(enabled, isError, interactionSource, colors),
            onValueChange = onValueChange,
            enabled = enabled,
            label = label,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            supportingText = helperText
        )

        if (isError && textError != null) {
            ErrorAnimation(value = textError, isError = isError)
        }
    }
}


@Composable
fun ErrorAnimation(
    value: String,
    isError: Boolean,
) {
    val density = LocalDensity.current
    MaxAnimatedVisibility(
        visible = isError,
        enter = slideInVertically(
            initialOffsetY = { with(density) { -1.dp.roundToPx() } },
        ) + expandVertically(
            expandFrom = Alignment.Bottom
        ),
        exit = slideOutVertically(
            targetOffsetY = {
                with(density) {
                    -1.dp.roundToPx()
                }
            },
            animationSpec = tween(700)
        ) + shrinkVertically(
            shrinkTowards = Alignment.Top
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .padding(vertical = 3.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
        }
    }
}


@Composable
fun MaxOutlinedTextFieldState(
    modifier: Modifier = Modifier,
    valueState: String,
    label: String,
    enabled: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onSecondary,
    ),
    maxLength: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    textError: String? = null,
    onChange: (String) -> Unit
) {
    MaxOutlinedTextField(
        modifier = modifier,
        value = valueState,
        onValueChange = {
            onChange(it.take(maxLength))
        },
        label = {
            Text(
                text = label,
            )
        },
        singleLine = singleLine,
        trailingIcon = trailingIcon,
        enabled = enabled,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        isError = isError,
        textError = textError,
    )
}

@Composable
fun UserCustomField(
    modifier: Modifier = Modifier,
    userValue: String,
    label: String = "Login",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    textError: String = "error",
    onChange: (String) -> Unit
) {
    MaxOutlinedTextFieldState(
        modifier = modifier,
        valueState = userValue,
        enabled = enabled,
        label = label,
        maxLength = 50,
        imeAction = imeAction,
        onAction = onAction,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.secondary
        ),
        isError = isError,
        textError = textError,
        onChange = onChange
    )
}

/*@Composable
fun EmailCustomField(
    modifier: Modifier = Modifier,
    userValue: MutableState<String>,
    label: String = "Insira seu e-mail",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    textError: String = "error"
) {
    Row(Modifier.fillMaxWidth()) {
        MaxOutlinedTextFieldState(
            modifier = modifier,
            valueState = userValue,
            enabled = enabled,
            label = label,
            maxLength = 25,
            imeAction = imeAction,
            onAction = onAction,
            textStyle = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Normal
            ),
            isError = isError,
            textError = textError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Localized icon",
                    tint = Color.Gray
                )
            }
        )
    }
}*/


@Composable
fun PasswordCustomField(
    modifier: Modifier = Modifier,
    passwordValue: String,
    label: String = "Senha",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    textError: String = "error",
    onChange: (String) -> Unit
) {

    val visibility = remember {
        mutableStateOf(false)
    }

    val icon = if (visibility.value) painterResource(MR.images.visibility_off) else painterResource(MR.images.visibility)

    Row(Modifier.fillMaxWidth()) {
        MaxOutlinedTextFieldState(
            modifier = modifier,
            valueState = passwordValue,
            label = label,
            enabled = enabled,
            imeAction = imeAction,
            onAction = onAction,
            visualTransformation = if (visibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isError,
            textError = textError,
            trailingIcon = {
                IconButton(onClick = {
                    visibility.value = !visibility.value
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "user_passowrd",
                        tint = Color.Gray
                    )
                }
            },
            onChange = onChange
        )
    }
}
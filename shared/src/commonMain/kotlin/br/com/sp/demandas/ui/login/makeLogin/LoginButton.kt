package br.com.sp.demandas.ui.login.makeLogin
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.components.MaxButton

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MaxButton(
            modifier = modifier.fillMaxWidth().height(50.dp),
            text = "Entrar",
            loading = enabled,
            enabled = enabled,
            onClick = onClick
        )
    }
}



@Composable
fun LoginTextButton(
    modifier: Modifier = Modifier,
    horizontaArrangement: Arrangement.Horizontal = Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    text: String = "Esqueceu a senha?",
    style: TextStyle = MaterialTheme.typography.bodySmall,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClickText: () -> Unit
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontaArrangement,
        verticalAlignment = verticalAlignment
    ) {
        TextButton(onClick = { onClickText() }) {
            Text(
                text = text,
                style = style,
                color = color
            )
        }
    }
}
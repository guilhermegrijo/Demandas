package br.com.sp.demandas.ui.login.changePassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.components.MaxButton


@Composable
fun ButtonAction(
    onClick: () -> Unit,
    enabled : Boolean,
    loading : Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MaxButton(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            text = "Enviar",
            enabled = enabled,
            onClick = onClick
        )
    }
}
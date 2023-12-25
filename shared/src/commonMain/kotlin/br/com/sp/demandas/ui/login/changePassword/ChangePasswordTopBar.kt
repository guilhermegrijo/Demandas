package br.com.sp.demandas.ui.login.changePassword

import androidx.compose.runtime.Composable
import br.com.sp.demandas.design.components.MaxTopAppBarNavigation

@Composable
fun EsqueceuSenhaTopAppBar(
    onVoltarEvent: () -> Unit
){
    MaxTopAppBarNavigation(
        onBackClick = onVoltarEvent,
        title = "Trocar Senha",
    )

}
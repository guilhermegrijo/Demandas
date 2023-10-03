package br.com.sp.demandas

import androidx.compose.ui.window.ComposeUIViewController
import br.com.sp.demandas.design.theme.MaxTheme
import br.com.sp.demandas.ui.App
import br.com.sp.demandas.ui.login.makeLogin.LoginScreen
import cafe.adriel.voyager.navigator.Navigator

fun MainViewController() = ComposeUIViewController {
    MaxTheme {
        Navigator(LoginScreen())
    }
}
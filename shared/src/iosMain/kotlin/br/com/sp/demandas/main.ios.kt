package br.com.sp.demandas

import androidx.compose.ui.window.ComposeUIViewController
import br.com.sp.demandas.core.IosFCMToken
import br.com.sp.demandas.core.app.FcmToken
import br.com.sp.demandas.design.theme.MaxTheme
import br.com.sp.demandas.ui.App
import br.com.sp.demandas.ui.login.makeLogin.LoginScreen
import cafe.adriel.voyager.navigator.Navigator
import org.koin.core.component.KoinComponent
import org.koin.mp.KoinPlatform
import cocoapods.FirebaseCore.*

fun MainViewController() = ComposeUIViewController {
    MaxTheme {
        Navigator(LoginScreen())
    }
}


fun setIosToken(token: String) {
        FIRApp.defaultApp()
    val koin = KoinPlatform.getKoin()
    val iosFCMToken = koin.get<IosFCMToken>()
    iosFCMToken.setToken(token)
}
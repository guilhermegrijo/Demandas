package br.com.sp.demandas.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import br.com.sp.demandas.design.theme.MaxTheme
import br.com.sp.demandas.ui.demandas.DemandasUI
import br.com.sp.demandas.ui.detalheDemanda.DetalheDemandaScreen
import br.com.sp.demandas.ui.home.HomeScreen
import br.com.sp.demandas.ui.login.changePassword.ChangePasswordUI
import br.com.sp.demandas.ui.login.checkCode.CheckCodeUI
import br.com.sp.demandas.ui.login.firstAccess.FirstAccessUI
import br.com.sp.demandas.ui.login.makeLogin.LoginScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun App() =
     LoginScreen()
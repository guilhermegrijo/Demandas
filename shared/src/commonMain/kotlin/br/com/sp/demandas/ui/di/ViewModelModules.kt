package br.com.sp.demandas.ui.di

import ForgotPasswordViewModel
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.demandas.DemandaViewModel
import br.com.sp.demandas.ui.detalheDemanda.DetalheDemandaViewModel
import br.com.sp.demandas.ui.home.HomeViewModel
import br.com.sp.demandas.ui.login.changePassword.ChangePasswordViewModel
import br.com.sp.demandas.ui.login.changedPassword.ChangedPasswordViewModel
import br.com.sp.demandas.ui.login.checkCode.CheckCodeViewModel
import br.com.sp.demandas.ui.login.firstAccess.FirstAccessViewModel
import br.com.sp.demandas.ui.login.makeLogin.LoginViewModel
import br.com.sp.demandas.ui.mensagens.MensagemViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { params -> LoginViewModel(get(), params.get(), get(), get(), get(), get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { params -> CheckCodeViewModel(get(), params.get()) }
    factory { params -> ChangePasswordViewModel(get(),get(), params.get(), params.get()) }
    factory { HomeViewModel(get()) }
    factory { DemandaViewModel(get(), get()) }
    factory { params -> DetalheDemandaViewModel(get(), params.get()) }
    factory { MensagemViewModel(get()) }
    factory { ChangedPasswordViewModel() }
    factory { FirstAccessViewModel() }
}
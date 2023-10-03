package br.com.sp.demandas.ui.di

import ForgotPasswordViewModel
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.demandas.DemandaViewModel
import br.com.sp.demandas.ui.detalheDemanda.DetalheDemandaViewModel
import br.com.sp.demandas.ui.home.HomeViewModel
import br.com.sp.demandas.ui.login.checkCode.CheckCodeViewModel
import br.com.sp.demandas.ui.login.makeLogin.LoginViewModel
import br.com.sp.demandas.ui.mensagens.MensagemViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { params -> LoginViewModel(get(), params.get(), get(), get(), get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { params -> CheckCodeViewModel(get(), params.get()) }
    factory { HomeViewModel() }
    factory { DemandaViewModel(get()) }
    factory { params -> DetalheDemandaViewModel(get(), params.get()) }
    factory { MensagemViewModel(get()) }
}
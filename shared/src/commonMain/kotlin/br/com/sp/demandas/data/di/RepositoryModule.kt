package br.com.sp.demandas.data.di

import br.com.sp.demandas.data.auth.AuthRepository
import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.data.demandas.DemandaRepository
import br.com.sp.demandas.data.filtroDemanda.FiltroDemandaRepository
import br.com.sp.demandas.data.mensagem.MensagemRepository
import br.com.sp.demandas.data.user.UserRepository
import br.com.sp.demandas.data.user.local.UserSettings
import br.com.sp.demandas.domain.auth.IAuthRepository
import br.com.sp.demandas.domain.demandas.IDemandaRepository
import br.com.sp.demandas.domain.filtroDemanda.IFiltroDemandaRepository
import br.com.sp.demandas.domain.mensagem.IMensagemRepository
import br.com.sp.demandas.domain.user.IUserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<IAuthRepository> { AuthRepository(get(), get(), get(), get()) }
    factory<IDemandaRepository> { DemandaRepository(get()) }
    factory { UserSettings(get()) }
    factory<IUserRepository> { UserRepository(get()) }
    factory { AuthCredentials(get()) }

    factory<IFiltroDemandaRepository> { FiltroDemandaRepository(get(), get(), get(), get(),get()) }
    factory<IMensagemRepository> { MensagemRepository(get(), get())}
}
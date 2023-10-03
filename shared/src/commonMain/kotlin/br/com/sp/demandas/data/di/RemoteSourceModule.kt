package br.com.sp.demandas.data.di

import br.com.sp.demandas.data.auth.remote.LoginRemoteSource
import br.com.sp.demandas.data.demandas.remote.DemandaRemoteSource
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeConvenioFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeEtapaFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeStatusFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.HierarquiaPrefeituraRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.HierarquiaRegionalRemoteDataSource
import br.com.sp.demandas.data.mensagem.remote.MensagemRemoteDataSource
import org.koin.dsl.module

val remoteSourceModule = module {
    factory { LoginRemoteSource(get()) }
    factory { DemandaRemoteSource(get(), get()) }
    factory { HierarquiaRegionalRemoteDataSource(get(), get()) }
    factory { HierarquiaPrefeituraRemoteDataSource(get(), get()) }
    factory { AtividadeStatusFiltroRemoteDataSource(get(), get()) }
    factory { AtividadeEtapaFiltroRemoteDataSource(get(), get()) }
    factory { AtividadeConvenioFiltroRemoteDataSource(get(), get()) }
    factory { MensagemRemoteDataSource(get(), get()) }
}
package br.com.sp.demandas.core.di

import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.data.auth.local.AuthCredentials
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module
    get() = module {
        includes(networkModule + platformCoreModule + UserModule)
    }

val UserModule = module {
    single { AuthCredentials(get(), ) }
}


internal expect val platformCoreModule: Module
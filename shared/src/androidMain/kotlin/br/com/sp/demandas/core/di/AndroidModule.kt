package br.com.sp.demandas.core.di

import br.com.sp.demandas.AndroidKeyValueStorage
import br.com.sp.demandas.AndroidPlatform
import br.com.sp.demandas.AndroidFCMToken
import br.com.sp.demandas.core.app.KeyValueStorage
import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.core.app.FcmToken
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformCoreModule: Module = module {
    singleOf(::AndroidKeyValueStorage) bind KeyValueStorage::class
    singleOf(::AndroidPlatform) bind Platform::class
    singleOf(::AndroidFCMToken) bind FcmToken::class
}
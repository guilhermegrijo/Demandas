package br.com.sp.demandas.core.di

import br.com.sp.demandas.core.app.KeyValueStorage
import br.com.sp.demandas.core.app.Platform
import com.liftric.kvault.KVault
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.UIKit.UIDevice


internal actual val platformCoreModule: Module = module {
    singleOf(::IosKeyValueStorage) bind KeyValueStorage::class
    singleOf(::IOSPlatform) bind Platform::class
}

class IosKeyValueStorage : KeyValueStorage {
    override fun getStore(): KVault {
        return KVault()
    }
}

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName()
}
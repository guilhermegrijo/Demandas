package br.com.sp.demandas.core.di

import br.com.sp.demandas.core.app.FcmToken
import br.com.sp.demandas.core.app.KeyValueStorage
import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.core.IosFCMToken
import com.liftric.kvault.KVault
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSUUID
import platform.UIKit.UIDevice


internal actual val platformCoreModule: Module = module {
    singleOf(::IosKeyValueStorage) bind KeyValueStorage::class
    singleOf(::IOSPlatform) bind Platform::class
    singleOf(::IosFCMToken) bind FcmToken::class
}

class IosKeyValueStorage : KeyValueStorage {
    override fun getStore(): KVault {
        return KVault()
    }
}

class IOSPlatform : Platform {
    override val name: String = "ios"
    override fun randomUUID() = NSUUID().UUIDString()

}
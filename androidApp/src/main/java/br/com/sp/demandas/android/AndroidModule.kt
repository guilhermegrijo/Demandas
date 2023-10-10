package br.com.sp.demandas.android

import br.com.sp.demandas.MessageInterface
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {
    singleOf(::NotificationService) bind MessageInterface::class
}
package br.com.sp.demandas

import br.com.sp.demandas.core.di.coreModule
import br.com.sp.demandas.data.di.remoteSourceModule
import br.com.sp.demandas.data.di.repositoryModule
import br.com.sp.demandas.domain.di.dispatcherModule
import br.com.sp.demandas.domain.di.useCasesModule
import br.com.sp.demandas.ui.di.viewModelModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            coreModule,
            viewModelModule,
            useCasesModule,
            repositoryModule,
            remoteSourceModule,
            dispatcherModule
        )

    }
}


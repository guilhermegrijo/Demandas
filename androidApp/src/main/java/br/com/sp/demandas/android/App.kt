package br.com.sp.demandas.android

import android.app.Application
import br.com.sp.demandas.core.di.coreModule
import br.com.sp.demandas.data.di.remoteSourceModule
import br.com.sp.demandas.data.di.repositoryModule
import br.com.sp.demandas.domain.di.dispatcherModule
import br.com.sp.demandas.domain.di.useCasesModule
import br.com.sp.demandas.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
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
}
package br.com.sp.demandas.domain.di

import br.com.sp.demandas.domain.auth.ChangePasswordUseCase
import br.com.sp.demandas.domain.auth.CheckCodeUseCase
import br.com.sp.demandas.domain.auth.DoLoginUseCase
import br.com.sp.demandas.domain.auth.ForgotPasswordUseCase
import br.com.sp.demandas.domain.auth.RefreshTokenUseCase
import br.com.sp.demandas.domain.auth.UpdateUserUseCase
import br.com.sp.demandas.domain.demandas.GetDemandaDetalheUseCase
import br.com.sp.demandas.domain.filtroDemanda.FiltroDemandasUseCase
import br.com.sp.demandas.domain.filtroDemanda.GetDemandasFiltroUseCase
import br.com.sp.demandas.domain.filtroDemanda.GetHierarquiaPrefeituraFiltroUseCase
import br.com.sp.demandas.domain.filtroDemanda.GetHierarquiaRegionalUseCase
import br.com.sp.demandas.domain.mensagem.GetMensagensLocalUseCase
import br.com.sp.demandas.domain.mensagem.GetMensagensUseCase
import br.com.sp.demandas.domain.user.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val useCasesModule = module {
    factory { DoLoginUseCase(get(), get()) }
    factory { ForgotPasswordUseCase(get(), get()) }
    factory { CheckCodeUseCase(get(), get()) }
    factory { ChangePasswordUseCase(get(), get()) }
    factory { GetUserUseCase(get(), get()) }
    factory { UpdateUserUseCase(get(), get()) }
    factory { RefreshTokenUseCase(get(), get(), get()) }

    factory { GetDemandasFiltroUseCase(get(), get(), get()) }
    factory { FiltroDemandasUseCase(get(), get(), get()) }
    factory { GetHierarquiaRegionalUseCase(get(), get()) }
    factory { GetHierarquiaPrefeituraFiltroUseCase(get(), get()) }

    factory { GetDemandaDetalheUseCase(get(), get()) }

    factory { GetMensagensUseCase(get(), get()) }
    factory { GetMensagensLocalUseCase(get(), get()) }

}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}
package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetAtividadeEtapaFiltroUseCase(dispatcher: CoroutineDispatcher) : BaseUseCase<Unit,Unit>(dispatcher) {
    override suspend fun block(param: Unit) {

    }
}
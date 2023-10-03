package br.com.sp.demandas.domain.demandas

import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetDemandaDetalheUseCase(
    private val demandaRepository: IDemandaRepository,
    dispatcher: CoroutineDispatcher
) :
    BaseUseCase<String, DemandaDetalhe>(dispatcher) {
    override suspend fun block(param: String): DemandaDetalhe {
        val demandaDetalhe = demandaRepository.getDemandaDetalhe(param)
        val etapaPlanejada = demandaRepository.getEtapaPlanejada(param)
        return demandaDetalhe.copy(etapaPlanejada = etapaPlanejada)
    }
}
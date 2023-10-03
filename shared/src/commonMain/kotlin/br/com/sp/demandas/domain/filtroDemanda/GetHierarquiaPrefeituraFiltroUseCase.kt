package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import kotlinx.coroutines.CoroutineDispatcher

class GetHierarquiaPrefeituraFiltroUseCase (
    private val filtroDemandaRepository: IFiltroDemandaRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Int, List<Filtro>>(dispatcher) {
    override suspend fun block(param: Int): List<Filtro> {
        return filtroDemandaRepository.getHierarquiaPrefeitura(param)
    }
}
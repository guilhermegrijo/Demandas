package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import kotlinx.coroutines.CoroutineDispatcher

class GetAtividadeStatusFiltroUseCase(
    private val filtroDemandaRepository: IFiltroDemandaRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, List<Filtro>>(dispatcher) {
    override suspend fun block(param: Unit) : List<Filtro> {
            return filtroDemandaRepository.getAtividadeStatusFiltro()
    }
}
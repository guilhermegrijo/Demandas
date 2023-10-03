package br.com.sp.demandas.domain.mensagem

import br.com.sp.demandas.data.mensagem.MensagemRepository
import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetMensagensUseCase(
    private val mensagemRepository: IMensagemRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, List<Mensagem>>(dispatcher) {
    override suspend fun block(param: Unit): List<Mensagem> {
        return mensagemRepository.buscarMensagem()
    }
}
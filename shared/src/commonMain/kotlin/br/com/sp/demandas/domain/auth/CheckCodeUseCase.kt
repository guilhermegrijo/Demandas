package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.core.MensagemRetorno
import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.auth.model.CheckCodeModel
import kotlinx.coroutines.CoroutineDispatcher

class CheckCodeUseCase(private val repository: IAuthRepository, dispatcher: CoroutineDispatcher) : BaseUseCase<CheckCodeModel, MensagemRetorno>(dispatcher) {
    override suspend fun block(param: CheckCodeModel): MensagemRetorno {
        return repository.checkCode(param.code, param.user)
    }
}
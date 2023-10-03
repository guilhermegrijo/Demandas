package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.auth.model.CheckCodeModel
import kotlinx.coroutines.CoroutineDispatcher

class CheckCodeUseCase(private val repository: IAuthRepository, dispatcher: CoroutineDispatcher) : BaseUseCase<CheckCodeModel, Unit>(dispatcher) {
    override suspend fun block(param: CheckCodeModel) {
        repository.checkCode(param.code, param.user)
    }
}
package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.domain.auth.model.CheckCodeModel
import br.com.sp.demandas.domain.auth.model.TrocarSenhaModel
import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class ChangePasswordUseCase(private val repository: IAuthRepository, dispatcher: CoroutineDispatcher) : BaseUseCase<TrocarSenhaModel, Unit>(dispatcher) {
    override suspend fun block(param: TrocarSenhaModel) {
        repository.trocarSenha(param.email,param.senha, param.guid)
    }
}
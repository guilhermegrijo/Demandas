package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.user.User
import kotlinx.coroutines.CoroutineDispatcher

class DoLoginUseCase(private val repository: IAuthRepository, dispatcher: CoroutineDispatcher) :
    BaseUseCase<LoginModel, User>(dispatcher) {
    override suspend fun block(param: LoginModel): User {
       return repository.doLogin(param)
    }
}
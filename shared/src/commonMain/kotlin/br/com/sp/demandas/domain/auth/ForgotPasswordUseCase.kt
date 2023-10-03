package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class ForgotPasswordUseCase(
    private val repository: IAuthRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<String, String>(dispatcher) {
    override suspend fun block(param: String): String {
        return repository.forgotPassword(param)
    }
}
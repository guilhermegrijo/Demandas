package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.domain.base.BaseUseCase
import br.com.sp.demandas.domain.user.IUserRepository
import br.com.sp.demandas.domain.user.User
import kotlinx.coroutines.CoroutineDispatcher

class UpdateUserUseCase(
    private val userRepository: IUserRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<User, Unit>(dispatcher) {
    override suspend fun block(param: User) {
        userRepository.setUser(param)
    }
}
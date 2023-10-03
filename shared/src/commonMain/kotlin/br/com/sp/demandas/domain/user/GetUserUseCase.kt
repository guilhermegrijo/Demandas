package br.com.sp.demandas.domain.user

import br.com.sp.demandas.data.user.local.UserSettings
import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetUserUseCase(
    private val userSettings: UserSettings, dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, User?>(dispatcher) {
    override suspend fun block(param: Unit): User? = userSettings.getUser()

}
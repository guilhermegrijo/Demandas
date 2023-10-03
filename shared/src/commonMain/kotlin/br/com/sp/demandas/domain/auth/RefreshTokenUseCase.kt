package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.data.user.local.UserSettings
import br.com.sp.demandas.domain.auth.model.RefreshTokenModel
import br.com.sp.demandas.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class RefreshTokenUseCase(
    private val userSettings: UserSettings,
    private val authRepository: IAuthRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Unit>(dispatcher) {
    override suspend fun block(param: Unit) {
        val idUsuario = userSettings.getUser()?.idUsuario
        val idEmpresa = userSettings.getUser()?.idEmpresa
        authRepository.refreshToken(
            RefreshTokenModel(
                idUsuario.toString(),
                idEmpresa.toString(),
                "",
                ""
            )
        )

    }
}
package br.com.sp.demandas.data.auth

import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.app.FcmToken
import br.com.sp.demandas.data.auth.mapping.toUser
import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.data.auth.remote.LoginRemoteSource
import br.com.sp.demandas.domain.auth.IAuthRepository
import br.com.sp.demandas.domain.auth.model.RefreshTokenModel
import br.com.sp.demandas.domain.mensagem.IMensagemRepository
import br.com.sp.demandas.domain.user.User

internal class AuthRepository(
    private val remoteSource: LoginRemoteSource,
    private val authCredentials: AuthCredentials,
    private val fcmToken: FcmToken,
    private val mensagemRepository: IMensagemRepository
) : IAuthRepository {
    override suspend fun doLogin(loginModel: LoginModel): User {
        val result = remoteSource.doLogin(loginModel)
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                result.body.apply {
                    authCredentials.setCredentials(token, refreshToken)
                    mensagemRepository.enviarToken(fcmToken.getToken())
                }

                return result.body.toUser()

            }
        }
    }

    override suspend fun forgotPassword(usuario: String): String {
        val result = remoteSource.forgotPassword(usuario)
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorMessage}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw Throwable("${result.errorMessage}")
            }

            is ApiResponse.Success -> return usuario
        }
    }

    override suspend fun checkCode(code: String, usuario: String) {
        val result = remoteSource.checkCode(usuario, code)
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorMessage}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw Throwable("${result.errorMessage}")
            }

            is ApiResponse.Success -> return
        }
    }

    override suspend fun refreshToken(refreshTokenModel: RefreshTokenModel) {

        val refreshToken = authCredentials.getRefreshToken()
        val token = authCredentials.getToken()
        val result = remoteSource.refreshToken(
            refreshTokenModel.copy(
                token = token,
                refreshToken = refreshToken
            )
        )
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw Throwable("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                result.body.apply {
                    authCredentials.setCredentials(this.token, this.refreshToken)
                }
                return
            }
        }
    }


}
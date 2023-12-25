package br.com.sp.demandas.data.auth

import br.com.sp.demandas.data.auth.local.AuthCredentials
import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.MensagemRetorno
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.app.FcmToken
import br.com.sp.demandas.data.auth.mapping.toUser
import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.data.auth.remote.LoginRemoteSource
import br.com.sp.demandas.domain.auth.model.TrocarSenhaModel
import br.com.sp.demandas.domain.auth.IAuthRepository
import br.com.sp.demandas.domain.auth.model.RefreshTokenModel
import br.com.sp.demandas.domain.mensagem.IMensagemRepository
import br.com.sp.demandas.domain.user.User
import kotlinx.coroutines.delay

internal class AuthRepository(
    private val remoteSource: LoginRemoteSource,
    private val authCredentials: AuthCredentials,
    private val fcmToken: FcmToken,
    private val mensagemRepository: IMensagemRepository,
) : IAuthRepository {
    override suspend fun doLogin(loginModel: LoginModel): User {
        when (val result = remoteSource.doLogin(loginModel)) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody.mensagemRetorno}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                result.body.apply {
                    authCredentials.setCredentials(token, refreshToken)
                    delay(500)
                    mensagemRepository.enviarToken(this.idUsuario,fcmToken.getToken())
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
                throw ServerException("${result.errorBody.mensagemRetorno}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw Throwable("${result.errorMessage}")
            }

            is ApiResponse.Success -> return usuario
        }
    }

    override suspend fun checkCode(code: String, usuario: String): MensagemRetorno {
        val result = remoteSource.checkCode(usuario, code)
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody.mensagemRetorno}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw Throwable("${result.errorMessage}")
            }

            is ApiResponse.Success -> return result.body
        }
    }

    override suspend fun trocarSenha(usuario: String, senha: String, guid: String) {
        val result = remoteSource.trocarSenha(TrocarSenhaModel(usuario, guid, senha))
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody.mensagemRetorno}")
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
                throw ServerException("${result.errorBody.mensagemRetorno}")
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
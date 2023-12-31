package br.com.sp.demandas.data.mensagem

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.app.Platform
import br.com.sp.demandas.data.filtroDemanda.remote.toDomain
import br.com.sp.demandas.data.mensagem.remote.MensagemRemoteDataSource
import br.com.sp.demandas.data.mensagem.remote.TokenRequest
import br.com.sp.demandas.data.mensagem.remote.toDomain
import br.com.sp.demandas.data.user.local.UserSettings
import br.com.sp.demandas.domain.mensagem.IMensagemRepository
import br.com.sp.demandas.domain.mensagem.Mensagem

class MensagemRepository(
    private val remoteDataSource: MensagemRemoteDataSource,
    private val userSettings: UserSettings,
    private val platform: Platform
) : IMensagemRepository {

    val mensagem = mutableListOf<Mensagem>()
    override suspend fun buscarMensagem(): List<Mensagem> {
        val result = remoteDataSource.getMensagens()
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorMessage}")
            }

            is ApiResponse.Error.SerializationError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                mensagem.clear()
                mensagem += result.body.map { it.toDomain() }
                return mensagem

            }
        }
    }

    override suspend fun buscarMensagemLocal(): List<Mensagem> {
        if (mensagem.isEmpty()) {
            when (val result = remoteDataSource.getMensagens()) {
                is ApiResponse.Error.GenericError -> {
                    throw ClientException("${result.errorMessage}")
                }

                is ApiResponse.Error.HttpError -> {
                    throw ServerException("${result.errorMessage}")
                }

                is ApiResponse.Error.SerializationError -> {
                    throw ClientException("${result.errorMessage}")
                }

                is ApiResponse.Success -> {
                    mensagem.clear()
                    mensagem += result.body.map { it.toDomain() }
                    return mensagem

                }
            }
        } else return mensagem
    }

    override suspend fun enviarToken(idUsuario: Long, token: String) {

        val result = remoteDataSource.enviarToken(
            TokenRequest(
                idUsuario, token, platform.name
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
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                return
            }
        }
    }
}
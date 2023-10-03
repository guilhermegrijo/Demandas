package br.com.sp.demandas.data.demandas

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.data.demandas.remote.DemandaRemoteSource
import br.com.sp.demandas.domain.demandas.Demanda
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.EtapaPlanejada
import br.com.sp.demandas.domain.demandas.IDemandaRepository
import toDomain

internal class DemandaRepository(private val demandaRemoteSource: DemandaRemoteSource) :
    IDemandaRepository {
    override suspend fun getDemandas(
        idRegional: String?,
        idPrefeitura: String?,
        idAtividadeEtapa: String?,
        idConvenio: String?,
        idAtividadeStatus: String?,
        aviso: Boolean?,
        alerta: Boolean?,
        alarme: Boolean?,
        numeroDemanda: String?
    ): List<Demanda> {
        val result = demandaRemoteSource.getDemandas(
            idRegional,
            idPrefeitura,
            idAtividadeEtapa,
            idConvenio,
            idAtividadeStatus,
            aviso,
            alerta,
            alarme,
            numeroDemanda
        )
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
                return result.body.map { it.toDomain() }

            }
        }
    }

    override suspend fun getDemandaDetalhe(idDemanda: String): DemandaDetalhe {

        when (val result = demandaRemoteSource.getDemandaDetalhe(idDemanda)) {
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
                return result.body.toDomain()

            }
        }
    }

    override suspend fun getEtapaPlanejada(idDemanda: String): List<EtapaPlanejada> {
        when (val result = demandaRemoteSource.getEtapaPlanejada(idDemanda)) {
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
                return result.body.map { it.toDomain() }

            }
        }
    }
}
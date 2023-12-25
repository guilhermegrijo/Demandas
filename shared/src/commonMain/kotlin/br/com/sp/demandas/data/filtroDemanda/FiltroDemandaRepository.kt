package br.com.sp.demandas.data.filtroDemanda

import br.com.sp.demandas.core.ApiResponse
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeConvenioFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeEtapaFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.AtividadeStatusFiltroRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.HierarquiaPrefeituraRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.HierarquiaRegionalRemoteDataSource
import br.com.sp.demandas.data.filtroDemanda.remote.toDomain
import br.com.sp.demandas.domain.filtroDemanda.GetAtividadeEtapaFiltroUseCase
import br.com.sp.demandas.domain.filtroDemanda.GetAtividadeStatusFiltroUseCase
import br.com.sp.demandas.domain.filtroDemanda.IFiltroDemandaRepository
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro

class FiltroDemandaRepository(
    private val hierarquiaRegionalRemoteDataSource: HierarquiaRegionalRemoteDataSource,
    private val hierarquiaPrefeituraRemoteDataSource: HierarquiaPrefeituraRemoteDataSource,
    private val atividadeEtapaFiltroRemoteDataSource: AtividadeEtapaFiltroRemoteDataSource,
    private val atividadeStatusFiltroRemoteDataSource: AtividadeStatusFiltroRemoteDataSource,
    private val atividadeConvenioFiltroRemoteDataSource: AtividadeConvenioFiltroRemoteDataSource

) :
    IFiltroDemandaRepository {
    override suspend fun getAtividadeEtapaFiltro(): List<Filtro> {
        val result = atividadeEtapaFiltroRemoteDataSource.getAtividadeEtapaFiltro()
        when (result) {
            is ApiResponse.Error.GenericError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Error.HttpError -> {
                throw ServerException("${result.errorBody.mensagemRetorno}")            }

            is ApiResponse.Error.SerializationError -> {
                throw ClientException("${result.errorMessage}")
            }

            is ApiResponse.Success -> {
                return result.body.map { it.toDomain() }

            }
        }
    }

    override suspend fun getAtividadeStatusFiltro(): List<Filtro> {
        val result = atividadeStatusFiltroRemoteDataSource.getAtividadeStatusFiltro()
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
                return result.body.map { it.toDomain() }

            }
        }
    }

    override suspend fun getAtividadeConvenioFiltro(): List<Filtro> {
        val result = atividadeConvenioFiltroRemoteDataSource.getAtividadeConvenioFiltro()
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

    override suspend fun getHierarquiaRegional(): List<Filtro> {
        val result = hierarquiaRegionalRemoteDataSource.getHierarquiaRegional()
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

    override suspend fun getHierarquiaPrefeitura(idPrefeitura: Int): List<Filtro> {
        val result = hierarquiaPrefeituraRemoteDataSource.getHierarquiaPrefeitura(idPrefeitura)
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
                return if(result.body.isEmpty()) emptyList() else result.body.map { it.toDomain() }

            }
        }
    }
}
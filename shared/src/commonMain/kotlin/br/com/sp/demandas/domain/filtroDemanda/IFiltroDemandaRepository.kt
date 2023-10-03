package br.com.sp.demandas.domain.filtroDemanda

import br.com.sp.demandas.domain.filtroDemanda.model.Filtro

interface IFiltroDemandaRepository {

    suspend fun getAtividadeEtapaFiltro() : List<Filtro>

    suspend fun getAtividadeStatusFiltro() : List<Filtro>

    suspend fun getAtividadeConvenioFiltro() : List<Filtro>

    suspend fun getHierarquiaRegional() : List<Filtro>

    suspend fun getHierarquiaPrefeitura(idPrefeitura : Int) : List<Filtro>

}
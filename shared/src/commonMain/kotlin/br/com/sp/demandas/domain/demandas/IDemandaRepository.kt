package br.com.sp.demandas.domain.demandas

interface IDemandaRepository {
    suspend fun getDemandas(
        idRegional: String? = null,
        idPrefeitura: String? = null,
        idAtividadeEtapa: String? = null,
        idConvenio: String? = null,
        idAtividadeStatus: String? = null,
        aviso: Boolean? = null,
        alerta: Boolean? = null,
        alarme: Boolean? = null,
        noPrazo : Boolean? = null,
        numeroDemanda : String? = null,
    ): List<Demanda>


    suspend fun getDemandaDetalhe(
        idDemanda: String
    ): DemandaDetalhe

    suspend fun getEtapaPlanejada(
        idDemanda: String
    ): List<EtapaPlanejada>

}
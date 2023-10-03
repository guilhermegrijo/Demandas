package br.com.sp.demandas.domain.filtroDemanda.model

data class DemandaFiltroInput(
    val regional: Filtro? = null,
    val prefeitura: Filtro? = null,
    val idAtividadeStatus: Filtro? = null,
    val idAtividadeEtapa: Filtro? = null,
    val idConvenio: Filtro? = null,
    val aviso: Boolean? = null,
    val alerta: Boolean? = null,
    val alarme: Boolean? = null,
    val noPrazo: Boolean? = null,
    val numeroDemanda: String? = null
)

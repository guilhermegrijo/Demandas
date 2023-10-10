package br.com.sp.demandas.data.demandas.remote

import kotlinx.serialization.Serializable


@Serializable
data class DemandaResponse(
    val idAtividade: Long,
    val idAtividadePai: String?,
    val numero: String?,
    val portifolio: String?,
    val etapa: String?,
    val etapaCor: String?,
    val situacao: String?,
    val situacaoCor: String?,
    val demandante: String?,
    val dataCriacao: String?,
    val dataUltimaTramitacao: String?,
    val valorTotal: Double?,
    val valorEstado: Double?,
    val aviso: String?,
    val alerta: String?,
    val alarme: String?,
    val prazoPrevisto: String? = null,
    val prazoRealizado: String? = null,
    val prazoRestante: String? = null,
    val tipoAlerta: String? = null,
    val idAlertaClassificacao: Long,
    val mxlAtividadeMobileDetalhe: String?
)

package br.com.sp.demandas.core

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse (
    val codigoRetorno: String?,
    val mensagens: List<String>,
    val erro: String?,
    val statusCode: Long?
)

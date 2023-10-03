package br.com.sp.demandas.data.filtroDemanda.remote

import kotlinx.serialization.Serializable


@Serializable
data class FiltroResponse(
    val value : String,
    val text : String,
    val code : String? = null
)

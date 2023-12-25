package br.com.sp.demandas.data.mensagem.remote

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val idUsuario: Long?,
    val token: String,
    val plataforma : String
)

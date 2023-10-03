package br.com.sp.demandas.domain.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenModel(
    val idUsuario : String?,
    val idEmpresa : String?,
    val token : String?,
    val refreshToken : String?
)

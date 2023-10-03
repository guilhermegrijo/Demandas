package br.com.sp.demandas.data.auth.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val login: String,
    val token: String,
    val refreshToken: String,
    val idEmpresa: Long,
    val idUsuario: Long,
    val funcao: String,
    val logoUrl: String,
    val versaoMobile: String?,
    val dataExpiracao: String,
    val flagPrimeiroAcesso: Boolean,
    val flagBloqueado: Boolean,
    val flagEmailConfirmado: Boolean
)
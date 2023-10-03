package br.com.sp.demandas.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val idEmpresa: Long,
    val idUsuario: Long,
    val funcao: String,
    val logoUrl: String,
    val versaoMobile: String?,
    val dataExpiracao: String,
    val flagPrimeiroAcesso: Boolean,
    val flagBloqueado: Boolean,
    val flagEmailConfirmado: Boolean,
    val loginByBiometry : Boolean = false
)

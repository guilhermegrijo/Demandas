package br.com.sp.demandas.domain.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrocarSenhaModel(
    @SerialName("Email")
    val email : String,
    @SerialName("Guid")
    val guid : String,
    @SerialName("Senha")
    val senha : String
)

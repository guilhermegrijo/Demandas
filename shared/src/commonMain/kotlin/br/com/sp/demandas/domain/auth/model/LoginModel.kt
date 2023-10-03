package br.com.sp.demandas.domain.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val login: String,
    val password: String,
    val rememberMe: Boolean
)
package br.com.sp.demandas.domain.auth.model

data class CheckCodeModel(
    val user : String,
    val code : String
)

package br.com.sp.demandas.core.app

interface FcmToken {
    suspend fun getToken() : String
}
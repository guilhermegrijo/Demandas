package br.com.sp.demandas.core

import br.com.sp.demandas.core.app.FcmToken

class IosFCMToken : FcmToken {

    private var token : String? = ""
    override suspend fun getToken(): String {
        return token ?: ""
    }

    fun setToken(token : String) {
        this.token = token
    }
}
package br.com.sp.demandas.core

import br.com.sp.demandas.core.app.FcmToken

class IosFCMToken : FcmToken {
    override suspend fun getToken(): String {
        return ""
    }
}
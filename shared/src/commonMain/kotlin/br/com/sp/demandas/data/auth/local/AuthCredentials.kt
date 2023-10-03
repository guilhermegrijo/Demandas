package br.com.sp.demandas.data.auth.local

import br.com.sp.demandas.core.app.KeyValueStorage

class AuthCredentials(private val storage: KeyValueStorage) {

    fun setCredentials(token: String, refreshToken: String) {
        storage.getStore().set("token", token)
        storage.getStore().set("refreshToken", refreshToken)
    }

    fun getToken(): String? = storage.getStore().string("token")


    fun getRefreshToken(): String? = storage.getStore().string("refreshToken")


}
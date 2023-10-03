package br.com.sp.demandas.data.user.local

import br.com.sp.demandas.core.app.KeyValueStorage
import br.com.sp.demandas.domain.user.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserSettings (private val storage: KeyValueStorage) {

    fun setUser(user : User) {
        val json = Json.encodeToString(user)
        storage.getStore().set("user", json)
    }

    fun getUser() : User? {
          return storage.getStore().string("user")?.let { Json.decodeFromString<User>(it) }
    }

}
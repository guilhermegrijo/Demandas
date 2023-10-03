package br.com.sp.demandas.data.user

import br.com.sp.demandas.data.user.local.UserSettings
import br.com.sp.demandas.domain.user.IUserRepository
import br.com.sp.demandas.domain.user.User

class UserRepository(private val userSettings: UserSettings) : IUserRepository {
    override fun getUser(): User? {
        return userSettings.getUser()
    }

    override fun setUser(user: User) {
        return userSettings.setUser(user)
    }
}
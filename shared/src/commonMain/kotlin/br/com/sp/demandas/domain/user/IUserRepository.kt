package br.com.sp.demandas.domain.user

interface IUserRepository {

    fun getUser() : User?
    fun setUser(user : User)

}
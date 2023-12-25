package br.com.sp.demandas.domain.auth

import br.com.sp.demandas.core.MensagemRetorno
import br.com.sp.demandas.domain.auth.model.LoginModel
import br.com.sp.demandas.domain.auth.model.RefreshTokenModel
import br.com.sp.demandas.domain.user.User

interface IAuthRepository {

    suspend fun doLogin(loginModel: LoginModel) : User

    suspend fun forgotPassword(usuario : String) : String

    suspend fun checkCode(code : String, usuario : String): MensagemRetorno

    suspend fun trocarSenha(usuario : String, senha : String, guid : String)

    suspend fun refreshToken(refreshTokenModel: RefreshTokenModel)
}
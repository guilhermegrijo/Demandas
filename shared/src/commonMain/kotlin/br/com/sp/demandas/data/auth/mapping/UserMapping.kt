package br.com.sp.demandas.data.auth.mapping

import br.com.sp.demandas.data.auth.remote.LoginResponse
import br.com.sp.demandas.domain.user.User

fun LoginResponse.toUser() = User(
    login,
    idEmpresa,
    idUsuario,
    funcao,
    logoUrl,
    versaoMobile,
    dataExpiracao,
    flagPrimeiroAcesso,
    flagBloqueado,
    flagEmailConfirmado
)

package br.com.sp.demandas.ui.login.makeLogin

data class LoginState(
    val userName : String,
    val askEnrollBiometry : Boolean,
    val biometricAvaible : Boolean,

)

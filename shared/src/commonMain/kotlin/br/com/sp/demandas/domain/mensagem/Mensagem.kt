package br.com.sp.demandas.domain.mensagem

data class Mensagem (
    val idAlertaMensagem : Int,
    val atividadePortifolio: String,
    val dataAlertaIni: String,
    val dataAlertaFim: String,
    val atividadeNumero: String,
    val atividadeStatus: String,
    val atividadeEtapa: String,
    val atividadeEtapaCor: String,
    val alertaClassificacao: String,
    val telefone: String,
    val email: String,
    val mensagem: String,
    val dataRegistro: String,
)
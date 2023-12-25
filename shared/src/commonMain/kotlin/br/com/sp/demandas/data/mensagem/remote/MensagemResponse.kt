package br.com.sp.demandas.data.mensagem.remote

import kotlinx.serialization.Serializable

@Serializable
data class MensagemResponse(
    val idAlertaMensagem: Long,
    val nome: String,
    val telefone: String,
    val demanda: String,
    val status: String,
    val etapa: String,
    val etapaCor: String,
    val mensagem: String,
    val dataRegistro: String,
    val dataProcessamento: String? = null,
    val dataEnvio: String? = null,
    val dataRecebimento: String? = null,
    val dataLeitura: String? = null
)

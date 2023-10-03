package br.com.sp.demandas.data.mensagem.remote

import kotlinx.serialization.Serializable

@Serializable
data class MensagemResponse(
    val idAtividadeStatus: Long,
    val idAtividadeEtapa: Long,
    val idAlertaClassificacao: String? = null,
    val idAtividade: Long,
    val idHierarquia: Long,
    val usuarioNome: String? = null,
    val atividadePortifolio: String,
    val dataAlertaIni: String,
    val dataAlertaFim: String,
    val atividadeNumero: String,
    val atividadeStatus: String,
    val atividadeEtapa: String,
    val atividadeEtapaCor: String,
    val alertaClassificacao: String,
    val hierarquia: String,
    val idAlertaMensagem: Int,
    val idAlerta: Long,
    val idUsuario: Long,
    val telefone: String,
    val email: String,
    val mensagem: String,
    val chave: String? = null,
    val dataRegistro: String,
    val dataProcessamento: String? = null,
    val dataEnvio: String? = null,
    val dataRecebimento: String? = null,
    val dataLeitura: String? = null,
    val flagProcessamento: Boolean,
    val flagErro: Boolean,
    val statusProcessamento: String? = null,
    val idAlertaNavigation: String? = null,
    val idAtividadeNavigation: String? = null,
    val idAtividadeStatusNavigation: String? = null,
    val idHierarquiaNavigation: String? = null,
    val idUsuarioNavigation: String? = null
)

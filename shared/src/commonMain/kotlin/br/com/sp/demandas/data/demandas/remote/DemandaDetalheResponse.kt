package br.com.sp.demandas.data.demandas.remote

import br.com.sp.demandas.domain.demandas.Responsabilidade
import kotlinx.serialization.Serializable

@Serializable
data class DemandaDetalheResponse(
    val idAtividade: Long,
    val idAtividadePai: Long? = null,
    val numero: String,
    val portfolio: String? = null,
    val objeto: String? = null,
    val etapa: String,
    val etapaCor: String? = null,
    val processo: String? = null,
    val programa: String? = null,
    val convenio: String? = null,
    val situacao: String? = null,
    val nomeEmpresa: String? = null,
    val prioritariaDeGoverno: Boolean? = null,
    val cliente: String,
    val demandante: String,
    val solicitante: String? = null,
    val dataCriacao: String? = null,
    val dataUltimaTramitacao: String? = null,
    val valorLiberado: Double? = null,
    val valorEstado: Double? = null,
    val valorTotal: Double? = null,
    val valorEmenda: Double? = null,
    val valorContrapartida: Double? = null,
    val observacao: String,
    val mxlAtividadeMobileDetalhe: List<DemandaDetalheResponse>? = null,
    val etapas: List<EtapaResponse>? = null,
    val eventos: List<EventoResponse>? = null
)

@Serializable
data class EtapaResponse(
    val etapa: String,
    val data: String?,
    val cor: String? = null,
)

@Serializable
data class EventoResponse(
    val idAtividade: Long,
    val nomeStatus: String,
    val nomeStatusAnterior: String,
    val latitude: Double,
    val longitude: Double,
    val data: String,
    val dataInicio: String,
    val dataFim: String,
    val urlMarcadorMapa: String? = null,
    val tecnicoAlocado: String,
    val usuarioAlteracao: String,
    val observacao: String,
    val etapaNome : String,
    val etapaCor : String,
    val idAtividadeStatus: Long,
    val nome: String,
    val sigla: String? = null,
    val nomePda: String? = null,
    val siglaPda: String? = null,
    val siglaGantt: String? = null,
    val cor: String? = null,
    val corNaoDefinida: Boolean,
    val flagExec: Boolean,
    val flagEnc: Boolean,
    val idEmpresa: String? = null,
    val flagDespacho: Boolean,
    val execucao: String? = null,
    val encerrada: String? = null,
    val nomeEmpresa: String? = null,
    val despacho: String? = null
)

@Serializable
data class AtividadeEtapaPlanejadaResponse (
    val atividadeEtapaPlanejadaModel: List<EtapaPlanejadaResponse>,
    val responsabilidade: List<ResponsabilidadeResponse>?
)

@Serializable
data class EtapaPlanejadaResponse(
    val etapa: String,
    val cor: String,
    val icone: String,
    val listaData: List<String?>,
    val dataInicio: String? = null,
    val dataFim: String? = null,
    val dataInicioDiligencia: String? = null,
    val dataFimDiligencia: String? = null,
    val diasPrevistos: Long,
    val diasRealizados: Long,
    val diasPrevistosDiligencia: Long,
    val diasRealizadosDiligencia: Long,
    val prazoReal: Long,
    val prazoObjetivo: Long,
    val desvio: Long,
    val historico: List<HistoricoResponse>?,
    val responsabilidade: List<ResponsabilidadeResponse>?,
    val totalPrazoRealizado: Long,
    val totalPrazoPrevisto: Long,
    val totalPrazoVencido: Long,
    val datasEtapa: List<DatasEtapaResponse>? = emptyList(),
    val etapaAtual: Boolean
)

@Serializable
data class HistoricoResponse(
    val etapa: String,
    val dataInicio: String?,
    val dataFim: String?,
    val prazoDias: Long,
    val prazoReal: Long,
    val prazoObjetivo: Long,
    val desvio: Long,
    val situacao: String,
    val situacaoCor: String,
    val dataInicioDiligencia: String? = null,
    val dataFimDiligencia: String? = null,
    val prazoDiasDiligencia: Long
)

@Serializable
data class ResponsabilidadeResponse(
    val nome: String,
    val etapa: String? = null,
    val prazoPrevisto: Long,
    val prazoRealizado: Long,
    val prazoVencido: Long
)
@Serializable
data class DatasEtapaResponse (
    val etapa: String,
    val dataInicio: String?,
    val dataFim: String?
)
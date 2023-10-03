package br.com.sp.demandas.domain.demandas

import br.com.sp.demandas.data.demandas.remote.DemandaDetalheResponse

data class DemandaDetalhe(
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
    val valorLiberado: String? = null,
    val valorEstado: String? = null,
    val valorTotal: String? = null,
    val valorEmenda: String? = null,
    val valorContrapartida: String? = null,
    val observacao: String,
    val parcelas: List<DemandaDetalhe>? = null,
    val etapas : List<Etapa>? = null,
    val eventos : List<Evento>? = null,
    val etapaPlanejada: List<EtapaPlanejada>? = null

)

data class Etapa (
    val etapa: String,
    val data: String,
    val cor: String?,
    val dataMilisegundos : Long,
)

data class Evento (
    val idAtividade: Long,
    val nomeStatus: String,
    val nomeStatusAnterior: String,
    val latitude: Double,
    val longitude: Double,
    val data: String,
    val dataInicio: String,
    val dataFim: String,
    val urlMarcadorMapa: Any? = null,
    val tecnicoAlocado: String,
    val usuarioAlteracao: String,
    val observacao: String,
    val idAtividadeStatus: Long,
    val nome: String,
    val sigla: Any? = null,
    val nomePda: String? = null,
    val siglaPda: String? = null,
    val siglaGantt: Any? = null,
    val cor: String? = null,
    val corNaoDefinida: Boolean,
    val flagExec: Boolean,
    val flagEnc: Boolean,
    val idEmpresa: Any? = null,
    val flagDespacho: Boolean,
    val execucao: Any? = null,
    val encerrada: Any? = null,
    val nomeEmpresa: Any? = null,
    val despacho: Any? = null
)

data class EtapaPlanejada (
    val etapa: String,
    val cor: String?,
    val listaData : List<String>,
)
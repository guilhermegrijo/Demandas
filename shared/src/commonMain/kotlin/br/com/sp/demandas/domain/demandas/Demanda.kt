package br.com.sp.demandas.domain.demandas

data class Demanda(
    val idAtividade : String,
    val numeroDemanda : String,
    val portfolio: String,
    val etapa : String,
    val etapaCor : String?,
    val situacao : String,
    val situacaoColor : String,
    val demandante : String,
    val dataCriacao: String,
    val dataUltimaTramitacao : String,
    val valorTotal : String,
    val valorEstado: String,
    val aviso: String,
    val alerta: String,
    val alarme: String,
    val prazoPrevisto: String,
    val prazoRealizado: String,
    val prazoRestante: String,
    val tipoAlerta: String,
    val idAlertaClassificacao: Long,

)
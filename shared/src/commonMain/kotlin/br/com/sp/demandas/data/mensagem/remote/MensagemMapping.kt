package br.com.sp.demandas.data.mensagem.remote

import br.com.sp.demandas.core.format
import br.com.sp.demandas.domain.mensagem.Mensagem
import kotlinx.datetime.LocalDateTime

fun MensagemResponse.toDomain() = Mensagem(
    idAlertaMensagem,
    nome,
    telefone,
    demanda,
    status,
    etapa,
    etapaCor,
    mensagem,
    if (dataRegistro != null) LocalDateTime.parse(dataRegistro)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.hour}:${it.minute}" } else "",
    dataProcessamento
)
package br.com.sp.demandas.data.mensagem.remote

import br.com.sp.demandas.core.format
import br.com.sp.demandas.domain.mensagem.Mensagem
import kotlinx.datetime.LocalDateTime

fun MensagemResponse.toDomain() = Mensagem(
    idAlertaMensagem = idAlertaMensagem,
    atividadePortifolio,
    dataAlertaIni,
    dataAlertaFim,
    atividadeNumero,
    atividadeStatus,
    atividadeEtapa,
    atividadeEtapaCor,
    alertaClassificacao,
    telefone,
    email,
    mensagem,
    if (dataRegistro != null) LocalDateTime.parse(dataRegistro)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
)
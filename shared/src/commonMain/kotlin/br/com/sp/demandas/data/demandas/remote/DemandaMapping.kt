import br.com.sp.demandas.core.format
import br.com.sp.demandas.core.moneyFormat
import br.com.sp.demandas.data.demandas.remote.AtividadeEtapaPlanejadaResponse
import br.com.sp.demandas.data.demandas.remote.DatasEtapaResponse
import br.com.sp.demandas.data.demandas.remote.DemandaDetalheResponse
import br.com.sp.demandas.data.demandas.remote.DemandaResponse
import br.com.sp.demandas.data.demandas.remote.EtapaPlanejadaResponse
import br.com.sp.demandas.data.demandas.remote.EtapaResponse
import br.com.sp.demandas.data.demandas.remote.EventoResponse
import br.com.sp.demandas.data.demandas.remote.HistoricoResponse
import br.com.sp.demandas.data.demandas.remote.ResponsabilidadeResponse
import br.com.sp.demandas.domain.demandas.DatasEtapa
import br.com.sp.demandas.domain.demandas.Demanda
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.Etapa
import br.com.sp.demandas.domain.demandas.EtapaPlanejada
import br.com.sp.demandas.domain.demandas.EtapaPlanejadaResponsabilidadeDemanda
import br.com.sp.demandas.domain.demandas.Evento
import br.com.sp.demandas.domain.demandas.Historico
import br.com.sp.demandas.domain.demandas.Responsabilidade
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun DemandaResponse.toDomain() = Demanda(idAtividade.toString(),
    numero.toString(),
    portfolio.toString(),
    etapa.toString(),
    etapaCor,
    situacao.toString(),
    situacaoCor ?: "FFFFFF",
    demandante.toString(),
    if (dataCriacao != null) LocalDateTime.parse(dataCriacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (dataUltimaTramitacao != null) LocalDateTime.parse(dataUltimaTramitacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    if (valorTotal != null) moneyFormat(valorTotal) ?: "" else "",
    if (valorEstado != null) moneyFormat(valorEstado) ?: "" else "",
    aviso ?: "0",
    alerta ?: "0",
    alarme ?: "0",
    prazoPrevisto ?: " ",
    prazoRealizado ?: " ",
    prazoRestante ?: " ",
    tipoAlerta ?: "",
    idAlertaClassificacao)

fun DemandaDetalheResponse.toDomain() = DemandaDetalhe(idAtividade,
    idAtividadePai,
    numero,
    portfolio,
    objeto,
    etapa,
    etapaCor,
    processo,
    programa,
    convenio,
    situacao,
    nomeEmpresa,
    prioritariaDeGoverno,
    cliente,
    demandante,
    solicitante,
    if (dataCriacao != null) LocalDateTime.parse(dataCriacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    dataUltimaTramitacaoHora = if (dataUltimaTramitacao != null) LocalDateTime.parse(
        dataUltimaTramitacao
    ).let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    dataUltimaTramitacao = if (dataUltimaTramitacao != null) LocalDateTime.parse(
        dataUltimaTramitacao
    ).let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (valorLiberado != null) moneyFormat(valorLiberado) ?: "" else "",
    if (valorEstado != null) moneyFormat(valorEstado) ?: "" else "",
    if (valorTotal != null) moneyFormat(valorTotal) ?: "" else "",
    if (valorEmenda != null) moneyFormat(valorEmenda) ?: "" else "",
    if (valorContrapartida != null) moneyFormat(valorContrapartida) ?: "" else "",
    observacao,
    parcelas = mxlAtividadeMobileDetalhe?.map { it.toParcelas() },
    etapas = etapas?.map { it.toDomain() },
    eventos = eventos?.map { it.toDomain() })

fun DemandaDetalheResponse.toParcelas() = DemandaDetalhe(idAtividade,
    idAtividadePai,
    numero,
    portfolio,
    objeto,
    etapa,
    etapaCor,
    processo,
    programa,
    convenio,
    situacao,
    nomeEmpresa,
    prioritariaDeGoverno,
    cliente,
    demandante,
    solicitante,
    if (dataCriacao != null) LocalDateTime.parse(dataCriacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    dataUltimaTramitacaoHora = if (dataUltimaTramitacao != null) LocalDateTime.parse(
        dataUltimaTramitacao
    ).let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    dataUltimaTramitacao = if (dataUltimaTramitacao != null) LocalDateTime.parse(
        dataUltimaTramitacao
    ).let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (valorLiberado != null) moneyFormat(valorLiberado) ?: "" else "",
    if (valorEstado != null) moneyFormat(valorEstado) ?: "" else "",
    if (valorTotal != null) moneyFormat(valorTotal) ?: "" else "",
    if (valorEmenda != null) moneyFormat(valorEmenda) ?: "" else "",
    if (valorContrapartida != null) moneyFormat(valorContrapartida) ?: "" else "",
    observacao,
    etapas = etapas?.map { it.toDomain() },
    eventos = eventos?.map { it.toDomain() })


fun EtapaResponse.toDomain() = Etapa(
    etapa,
    if (data != null) LocalDateTime.parse(data)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    cor,
    if (data != null) LocalDateTime.parse(data).toInstant(TimeZone.UTC)
        .toEpochMilliseconds() else 0,
)


fun EventoResponse.toDomain() = Evento(idAtividade,
    nomeStatus,
    nomeStatusAnterior,
    latitude,
    longitude,
    if (data != null) LocalDateTime.parse(data)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    dataInicio,
    dataFim,
    urlMarcadorMapa,
    tecnicoAlocado,
    usuarioAlteracao,
    observacao,
    etapaNome,
    etapaCor,
    idAtividadeStatus,
    nome,
    sigla,
    nomePda,
    siglaPda,
    siglaGantt,
    cor,
    corNaoDefinida,
    flagExec,
    flagEnc,
    idEmpresa,
    flagDespacho)

fun AtividadeEtapaPlanejadaResponse.toDomain() = EtapaPlanejadaResponsabilidadeDemanda(
    atividadeEtapaPlanejadaModel.map { it.toDomain() },
    responsabilidade?.map { it.toDomain() })

fun EtapaPlanejadaResponse.toDomain() = EtapaPlanejada(etapa,
    cor,
    icone,
    listaData.map { data ->
        if(data != null)
        LocalDateTime.parse(data)
            .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" }
        else ""
    },
    if (dataInicio != null) LocalDateTime.parse(dataInicio)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (dataFim != null) LocalDateTime.parse(dataFim)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (dataInicioDiligencia != null) LocalDateTime.parse(dataInicioDiligencia)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    if (dataFimDiligencia != null) LocalDateTime.parse(dataFimDiligencia)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    diasPrevistos,
    diasRealizados,
    diasPrevistosDiligencia,
    diasRealizadosDiligencia,
    prazoReal,
    prazoObjetivo,
    desvio,
    historico?.map { it.toDomain() } ?: emptyList(),
    responsabilidade?.map { it.toDomain() } ?: emptyList(),
    totalPrazoRealizado,
    totalPrazoPrevisto,
    totalPrazoVencido,
    datasEtapa?.map { it.toDomain() },
    etapaAtual
)

fun HistoricoResponse.toDomain() = Historico(etapa,
    if (dataInicio != null) LocalDateTime.parse(dataInicio)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (dataFim != null) LocalDateTime.parse(dataFim)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    prazoDias,
    prazoReal,
    prazoObjetivo,
    desvio,
    situacao,
    situacaoCor,
    if (dataInicioDiligencia != null) LocalDateTime.parse(dataInicioDiligencia)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    if (dataFimDiligencia != null) LocalDateTime.parse(dataFimDiligencia)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    prazoDiasDiligencia)

fun ResponsabilidadeResponse.toDomain() = Responsabilidade(
    nome,
    etapa,
    prazoPrevisto.coerceAtLeast(0),
    prazoRealizado.coerceAtLeast(0),
    prazoVencido.coerceAtLeast(0)
)

fun DatasEtapaResponse.toDomain() = DatasEtapa(
    etapa,
    if (dataInicio != null) LocalDateTime.parse(dataInicio)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
    if (dataFim != null) LocalDateTime.parse(dataFim)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year}" } else "",
)
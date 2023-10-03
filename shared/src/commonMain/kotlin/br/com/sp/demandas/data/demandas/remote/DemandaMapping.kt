import br.com.sp.demandas.core.format
import br.com.sp.demandas.core.moneyFormat
import br.com.sp.demandas.data.demandas.remote.DemandaDetalheResponse
import br.com.sp.demandas.data.demandas.remote.DemandaResponse
import br.com.sp.demandas.data.demandas.remote.EtapaPlanejadaResponse
import br.com.sp.demandas.data.demandas.remote.EtapaResponse
import br.com.sp.demandas.data.demandas.remote.EventoResponse
import br.com.sp.demandas.domain.demandas.Demanda
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.Etapa
import br.com.sp.demandas.domain.demandas.EtapaPlanejada
import br.com.sp.demandas.domain.demandas.Evento
import io.fluidsonic.currency.Currency
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun DemandaResponse.toDomain() = Demanda(
    idAtividade.toString(),
    numero.toString(),
    portifolio.toString(),
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
    alarme ?: "",
    prazo
)

fun DemandaDetalheResponse.toDomain() = DemandaDetalhe(
    idAtividade,
    idAtividadePai,
    numero,
    portifolio,
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
    if (dataUltimaTramitacao != null) LocalDateTime.parse(dataUltimaTramitacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    if (valorLiberado != null) moneyFormat(valorLiberado) ?: "" else "",
    if (valorEstado != null) moneyFormat(valorEstado) ?: "" else "",
    if (valorTotal != null) moneyFormat(valorTotal) ?: "" else "",
    if (valorEmenda != null) moneyFormat(valorEmenda) ?: "" else "",
    if (valorContrapartida != null) moneyFormat(valorContrapartida) ?: "" else "",
    observacao,
    parcelas = mxlAtividadeMobileDetalhe?.map { it.toParcelas() },
    etapas = etapas?.map { it.toDomain() },
    eventos = eventos?.map { it.toDomain() }
)

fun DemandaDetalheResponse.toParcelas() = DemandaDetalhe(
    idAtividade,
    idAtividadePai,
    numero,
    portifolio,
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
    if (dataUltimaTramitacao != null) LocalDateTime.parse(dataUltimaTramitacao)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    if (valorLiberado != null) moneyFormat(valorLiberado) ?: "" else "",
    if (valorEstado != null) moneyFormat(valorEstado) ?: "" else "",
    if (valorTotal != null) moneyFormat(valorTotal) ?: "" else "",
    if (valorEmenda != null) moneyFormat(valorEmenda) ?: "" else "",
    if (valorContrapartida != null) moneyFormat(valorContrapartida) ?: "" else "",
    observacao,
    etapas = etapas?.map { it.toDomain() },
    eventos = eventos?.map { it.toDomain() }
)


fun EtapaResponse.toDomain() = Etapa(
    etapa,
    if (data != null) LocalDateTime.parse(data)
        .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" } else "",
    cor,
    if (data != null) LocalDateTime.parse(data).toInstant(TimeZone.UTC)
        .toEpochMilliseconds() else 0,
)


fun EventoResponse.toDomain() = Evento(
    idAtividade,
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
    flagDespacho
)

fun EtapaPlanejadaResponse.toDomain() = EtapaPlanejada(
    etapa,
    cor,
    listaData?.map { data ->
        LocalDateTime.parse(data)
            .let { "${it.dayOfMonth.format()}/${it.monthNumber.format()}/${it.year} ${it.time}" }
    } ?: emptyList()

)
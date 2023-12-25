package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.design.components.Table
import br.com.sp.demandas.design.components.timeline.TimelineNode
import br.com.sp.demandas.design.components.timeline.defaults.CircleParametersDefaults
import br.com.sp.demandas.design.components.timeline.defaults.LineParametersDefaults
import br.com.sp.demandas.design.components.timeline.model.TimelineNodePosition
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.EtapaPlanejada
import br.com.sp.demandas.domain.demandas.Responsabilidade
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.launch

internal class EtapasTab(
    private val viewModel: DetalheDemandaViewModel,
    private val onClickEtapa: (EtapaPlanejada) -> Unit,
    private val onClickHelp: () -> Unit
) : Tab {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val state by viewModel.uiState.collectAsState()
        val tooltipState = RichTooltipState()
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            when (state.state) {
                is ResourceUiState.Loading -> {
                    ShimmerPreview(8)
                }

                is ResourceUiState.Empty -> {}
                is ResourceUiState.Error -> {}
                ResourceUiState.Idle -> {}
                is ResourceUiState.Success -> {

                    val demanda = (state.state as ResourceUiState.Success<DemandaDetalhe>).data

                    val lazy = rememberLazyListState()

                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = BorderStroke(
                            1.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) {


                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Portfólio: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(demanda.portfolio)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )





                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Etapa Atual: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal, color = Color(
                                            demanda.etapaCor?.removePrefix(
                                                "#"
                                            )?.toLong(16)?.or(0x00000000FF000000)
                                                ?: 0xFFFFFFF
                                        )
                                    )
                                ) {
                                    append(demanda.etapa)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Data última tramitação: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal,
                                    )
                                ) {
                                    append(demanda.dataUltimaTramitacao)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Valor Total: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(demanda.valorTotal)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Valor Estado: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(demanda.valorEstado)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Parcelas aprovadas: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(demanda.valorEmenda)
                                }

                            },
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                        )

                        Spacer(Modifier.height(4.dp))

/*                        RichTooltipBox(colors = RichTooltipColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            actionContentColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            titleContentColor = MaterialTheme.colorScheme.onBackground
                        ), modifier = Modifier.fillMaxWidth(), action = {
                            TextButton(onClick = { scope.launch { tooltipState.dismiss() } }) {
                                Text(
                                    "Fechar"
                                )
                            }
                        }, title = {
                            Text(
                                text = "Observação",
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                ),
                            )
                        }, text = {
                            Text(
                                "1.O Prazo objetivo define em dias o desempenho esperado para o progresso de realização do CONVÊNIO.\n" + "2.O CVC - CICLO DE VIDA DO CONVÊNIO estabelece à partir da data início a duração de cada ETAPA desde a inclusão até o fechamento.\n" + "3.O prazo real é contado em cada ETAPA e comparado ao objetivo sendo apresentado por responsabilidades.\n" + "4.Cada ETAPA possui uma cor específica e é utilizada para sua identificação tem um circulo vermelho nas demandas com prazo vencido"
                            )
                        }, tooltipState = tooltipState
                        ) {
                            OutlinedButton(
                                { scope.launch { tooltipState.show() } },
                                modifier = Modifier.align(Alignment.End).fillMaxWidth().padding(8.dp),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                Text(" ?   ", modifier = Modifier)
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append("")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append("Ciclo de vida do convênio, Etapas e prazos")
                                        }

                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }*/

                        var people = demanda.etapaPlanejada?.responsabilidade ?: emptyList()
                        people = people.plus(Responsabilidade(
                            "Total",
                            "",
                            demanda.etapaPlanejada?.responsabilidade?.sumOf { it.prazoPrevisto }
                                ?: 0,
                            demanda.etapaPlanejada?.responsabilidade?.sumOf { it.prazoRealizado }
                                ?: 0,
                            demanda.etapaPlanejada?.responsabilidade?.sumOf { it.prazoVencido }
                                ?: 0,
                        ))
                        val cellWidth: (Int) -> Dp = { index ->
                            when (index) {
                                1 -> 80.dp
                                2 -> 60.dp
                                3 -> 80.dp
                                else -> 110.dp
                            }
                        }
                        val headerCellTitle: @Composable (Int) -> Unit = { index ->
                            val value = when (index) {
                                0 -> "Até a data"
                                1 -> "Objetivo"
                                2 -> "Real"
                                3 -> "Vencido"
                                else -> ""
                            }

                            Text(
                                text = value,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            )
                        }
                        val cellText: @Composable (Int, Responsabilidade) -> Unit = { index, item ->
                            val value = when (index) {
                                0 -> item.nome
                                1 -> item.prazoPrevisto
                                2 -> item.prazoRealizado
                                3 -> item.prazoVencido
                                else -> ""
                            }
                            if (index == 0) Text(
                                text = value.toString(),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            else Text(
                                text = value.toString(),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        Text(
                            text = "CVC - Ciclo de Vida do Convênio - duração em dias",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Normal
                            ),
                        )

                        Table(
                            columnCount = 4,
                            cellWidth = cellWidth,
                            data = people,
                            modifier = Modifier.verticalScroll(rememberScrollState())
                                .align(Alignment.CenterHorizontally),
                            headerCellContent = headerCellTitle,
                            cellContent = cellText
                        )


                    }
                    Text(
                        text = "Etapas do fluxo  - duração em dias",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Normal
                        ),
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
                        state = lazy,
                        horizontalAlignment = Alignment.Start
                    ) {

                        itemsIndexed(
                            demanda.etapaPlanejada?.atividadeEtapaPlanejadaModel ?: emptyList()
                        ) { index, item ->


                            TimelineNode(
                                position = when (index) {
                                    0 -> TimelineNodePosition.FIRST
                                    demanda.etapaPlanejada?.atividadeEtapaPlanejadaModel?.size?.minus(
                                        1
                                    ) ?: 0 -> TimelineNodePosition.LAST

                                    else -> TimelineNodePosition.MIDDLE
                                },
                                circleParameters = CircleParametersDefaults.circleParameters(
                                    backgroundColor = Color(
                                        item.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
                                    )
                                ),
                                lineParameters = if (index != (demanda.etapaPlanejada?.atividadeEtapaPlanejadaModel?.size?.minus(
                                        1
                                    ) ?: 0)
                                ) LineParametersDefaults.linearGradient(
                                    startColor = Color(
                                        item.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
                                    ), endColor = Color(
                                        demanda.etapaPlanejada?.atividadeEtapaPlanejadaModel?.get(
                                            index + 1
                                        )?.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
                                    )
                                ) else null,
                            ) { modifier ->
                                Column(modifier) {
                                    Text(
                                        text = item.etapa,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(8.dp)
                                            .clickable { onClickEtapa(item) },
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(
                                                item.cor?.removePrefix(
                                                    "#"
                                                )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
                                            ), fontWeight = FontWeight.Black
                                        ),
                                    )
                                    if (item.etapa == demanda.etapa) Text(
                                        text = "Situação atual: ${demanda.situacao}",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(8.dp)
                                            .clickable { onClickEtapa(item) },
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(
                                                item.cor?.removePrefix(
                                                    "#"
                                                )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
                                            ), fontWeight = FontWeight.Normal
                                        ),
                                    )

                                    Column {
                                        item.datasEtapa?.forEachIndexed { index, datasEtapa ->
                                            Column {
                                                Text(
                                                    text = "${index + 1} Tramitação:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.padding(horizontal = 8.dp),
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        fontWeight = FontWeight.SemiBold
                                                    ),
                                                )
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Start
                                                ) {

                                                    Text(
                                                        text = "Inicio: ${datasEtapa.dataInicio}",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.padding(horizontal = 8.dp),
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            color = MaterialTheme.colorScheme.onSurface,
                                                            fontWeight = FontWeight.Normal
                                                        ),
                                                    )
                                                    Text(
                                                        text = "Fim: ${datasEtapa.dataFim}",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.padding(horizontal = 8.dp),
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            color = MaterialTheme.colorScheme.onSurface,
                                                            fontWeight = FontWeight.Normal
                                                        ),
                                                    )
                                                }
                                            }

                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(
                                                text = "Prazo real: ${item.prazoReal}",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(horizontal = 8.dp),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.Normal
                                                ),
                                            )
                                            Text(
                                                text = "Prazo obj: ${item.prazoObjetivo}",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(horizontal = 8.dp),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.Normal
                                                ),
                                            )
                                        }

                                        Text(
                                            text = buildAnnotatedString {

                                                append("Prazo vencido: ")
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                ) {
                                                    append(item.desvio.toString())
                                                }

                                            },
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier.padding(horizontal = 8.dp),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontWeight = FontWeight.Normal
                                            ),
                                        )
                                    }

                                }
                            }
                        }


                    }
                }
            }
        }
    }

    override val options: TabOptions
        @Composable get() {
            val title = "Etapas"
            val icon = rememberVectorPainter(Icons.Default.DateRange)

            return remember {
                TabOptions(
                    index = 0u, title = title, icon = icon
                )
            }
        }
}

data class Prazos(
    val name: String, val objetivo: Int, val real: Int, val vencido: Int
)


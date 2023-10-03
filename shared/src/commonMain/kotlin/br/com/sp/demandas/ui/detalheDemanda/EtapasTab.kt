package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.design.components.timeline.TimelineNode
import br.com.sp.demandas.design.components.timeline.defaults.CircleParametersDefaults
import br.com.sp.demandas.design.components.timeline.defaults.LineParametersDefaults
import br.com.sp.demandas.design.components.timeline.model.TimelineNodePosition
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

internal class EtapasTab(private val viewModel: DetalheDemandaViewModel) : Tab {

    @Composable
    override fun Content() {

        val state by viewModel.uiState.collectAsState()
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

                    Text(
                        text = "Ciclo de vida do convênio",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Black
                        ),
                    )

                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground)
                    ) {



                        Spacer(Modifier.height(16.dp))

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
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
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
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))

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
                                        fontWeight = FontWeight.Normal,
                                        color = Color(
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
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))
                    }


                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(32.dp),
                        state = lazy,
                        horizontalAlignment = Alignment.Start
                    ) {
                        itemsIndexed(
                            demanda.etapaPlanejada ?: emptyList()
                        ) { index, item ->

                            TimelineNode(
                                position = when (index) {
                                    0 -> TimelineNodePosition.FIRST
                                    demanda.etapaPlanejada?.size?.minus(
                                        1
                                    ) ?: 0 -> TimelineNodePosition.LAST

                                    else -> TimelineNodePosition.MIDDLE
                                },
                                circleParameters = CircleParametersDefaults.circleParameters(
                                    backgroundColor = Color(
                                        item.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000)
                                            ?: 0xFFFFFFF
                                    )
                                ),
                                lineParameters = if (index != (demanda.etapaPlanejada?.size?.minus(1)
                                        ?: 0)
                                ) LineParametersDefaults.linearGradient(
                                    startColor = Color(
                                        item.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000)
                                            ?: 0xFFFFFFF
                                    ),
                                    endColor = Color(
                                        demanda.etapaPlanejada?.get(index + 1)?.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000)
                                            ?: 0xFFFFFFF
                                    )
                                ) else null,
                            ) { modifier ->
                                Column(modifier) {
                                    Text(
                                        text = item.etapa,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(8.dp),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(
                                                item.cor?.removePrefix(
                                                    "#"
                                                )?.toLong(16)?.or(0x00000000FF000000)
                                                    ?: 0xFFFFFFF
                                            ),
                                            fontWeight = FontWeight.Black
                                        ),
                                    )
                                    Column {
                                        item.listaData.forEach {
                                            Text(
                                                text = it,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(8.dp),
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
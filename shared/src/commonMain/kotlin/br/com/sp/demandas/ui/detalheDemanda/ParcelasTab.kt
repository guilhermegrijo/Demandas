package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

internal class ParcelasTab(private val viewModel: DetalheDemandaViewModel) : Tab {

    @Composable
    override fun Content() {

        val state by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
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

                    LazyColumn {
                        item {

                            Text(
                                text = "Detalhes de parcelas",
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


                            }
                        }
                        itemsIndexed(
                            items = demanda.parcelas ?: emptyList()
                        ) { index, demandaDetalhe ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp
                                ),
                                modifier = Modifier.padding(
                                    16.dp, 5.dp, 16.dp, 10.dp
                                ).fillMaxWidth(1f).clickable {
                                    viewModel.handleEvent(
                                        DetalheDemandaContract.Event.NavigateToParcelas(
                                            demandaDetalhe.idAtividade.toString()
                                        )
                                    )
                                },
                            ) {
                                Column(Modifier.padding(16.dp)) {

                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Número parcela: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(demandaDetalhe.numero)
                                            }


                                        },
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().padding(end = 6.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Parcela: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append((index + 1).toString())
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth().border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ).padding(8.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Valor: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(demandaDetalhe.valorTotal)
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth().border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ).padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Valor aprovado: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(demandaDetalhe.valorEmenda)
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth().border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ).padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Situação: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(
                                                    demandaDetalhe.situacao?.toLowerCase(Locale.current)
                                                        ?.capitalize(Locale.current) ?: ""
                                                )
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth().border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ).padding(8.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    androidx.compose.material.Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Data: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(demandaDetalhe.dataUltimaTramitacao)
                                            }
                                        },
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.fillMaxWidth().border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ).padding(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }


                }


            }
        }
    }


    override val options: TabOptions
        @Composable
        get() {
            val title = "Parcelas"
            val icon = rememberVectorPainter(Icons.Default.List)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}
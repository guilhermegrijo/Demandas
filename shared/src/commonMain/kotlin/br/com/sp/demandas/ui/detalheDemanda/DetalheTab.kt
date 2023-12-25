package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
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
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.Responsabilidade
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.launch

internal class DetalheTab(private val viewModel: DetalheDemandaViewModel) : Tab {

    @Composable
    override fun Content() {

        val state by viewModel.uiState.collectAsState()
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            when (state.state) {
                is ResourceUiState.Loading -> {
                    item { ShimmerPreview(8) }

                }

                is ResourceUiState.Empty -> {}
                is ResourceUiState.Error -> {}
                ResourceUiState.Idle -> {}
                is ResourceUiState.Success -> {
                    val demanda = (state.state as ResourceUiState.Success<DemandaDetalhe>).data




                    item {

                        Card(
                            modifier = Modifier.padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground)
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
                                        append("Etapa Atual: ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Normal, color = Color(
                                                demanda.etapaCor?.removePrefix(
                                                    "#"
                                                )?.toLong(16)?.or(0x00000000FF000000) ?: 0xFFFFFFF
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
                            Spacer(Modifier.height(4.dp))

                        }

                            Column(
                                modifier = Modifier.padding(horizontal = 24.dp
                                ).fillMaxWidth(1f)
                            ) {



                                androidx.compose.material.Text(
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
                                            append(demanda.portfolio ?: "")
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
                                            append("Demandante: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.demandante)
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
                                            append("Processo: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.processo ?: "")
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
                                            append("Convênio: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.convenio ?: "")
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
                                            append("Programa: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.programa)
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
                                            append("Etapa: ")
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
                                            append("Estado atual: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                            )
                                        ) {
                                            append(demanda.situacao)
                                        }
                                    },
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth().border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    ).padding(8.dp)
                                )
/*
                                androidx.compose.material.Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append("Secretária: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.nomeEmpresa)
                                        }
                                    },
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth().border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    ).padding(8.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))*/

                               /*
                                Spacer(modifier = Modifier.height(8.dp))
                                androidx.compose.material.Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append("Solicitante: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.solicitante ?: "")
                                        }
                                    },
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth().border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = RoundedCornerShape(4.dp)
                                    ).padding(8.dp)
                                )*/
                                Spacer(modifier = Modifier.height(8.dp))

                                androidx.compose.material.Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append("Data de criação: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.dataCriacao)
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
                                            append("Data da última tramitação: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.dataUltimaTramitacaoHora)
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
                                            append("Valor total: ")
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
                                            append("Valor do estado: ")
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
                                            append("Total de parcelas aprovadas: ")
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

    override val options: TabOptions
        @Composable
        get() {
            val title = "Demanda"
            val icon = rememberVectorPainter(Icons.Default.AccountBox)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}
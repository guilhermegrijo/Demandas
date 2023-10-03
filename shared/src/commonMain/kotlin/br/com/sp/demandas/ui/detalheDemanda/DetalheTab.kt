package br.com.sp.demandas.ui.detalheDemanda

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

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
                            Column(
                                modifier = Modifier.padding(24.dp
                                ).fillMaxWidth(1f)
                            ) {
                                Spacer(Modifier.height(16.dp))

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
                                Spacer(modifier = Modifier.height(8.dp))

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
                                )/*
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
                                            append(demanda.dataUltimaTramitacao)
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
                                            append("Valor contrapartida: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.valorContrapartida)
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
                                            append("Valor estado: ")
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
                                            append("Valor liberado: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append(demanda.valorLiberado)
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
            val title = "Detalhes"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}
package br.com.sp.demandas.ui.detalheMensagem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxOutlinedTextField
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.mensagens.CardNotificacao
import br.com.sp.demandas.ui.mensagens.MensagemScreenContract
import br.com.sp.demandas.ui.mensagens.MensagemViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

class DetalheMensagemUI(private val mensagem: Mensagem) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                MaxTopAppBarCenter(title = {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.weight(0.7f),
                            painter = painterResource(MR.images.sp_mini),
                            contentDescription = "logo"
                        )
                        Text("SGRI", fontWeight = FontWeight.SemiBold)
                    }


                }, navigationIcon = {
                    IconButton(onClick = {
                        navigator.pop()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Sair do sistema"

                        )
                    }
                }, actions = {
                })
            },

            snackbarHost = {
                SnackbarHost(scaffoldState.snackbarHostState) {
                    Snackbar(it.actionLabel ?: "", it.message, snackbarType.value) {
                        it.dismiss()
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background).padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {

                Text(
                    text = "Demanda: ${mensagem.demanda}",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Black
                    ),
                )

                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background).padding(
                            24.dp
                        ).fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    item {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Data de registro: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal,
                                    )
                                ) {
                                    append(mensagem.dataRegistro)
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
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Classificação: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal,
                                        color = Color(
                                            mensagem.etapaCor?.removePrefix(
                                                "#"
                                            )?.toLong(16)?.or(0x00000000FF000000)
                                                ?: 0xFFFFFFF
                                        ),
                                    )
                                ) {
                                    append(mensagem.status)
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
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Mensagem: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Normal,
                                    )
                                ) {
                                    append(mensagem.mensagem)
                                }


                            },
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth().border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(4.dp)
                            ).padding(8.dp)
                        )
                    }
                }
            }

        }
    }
}
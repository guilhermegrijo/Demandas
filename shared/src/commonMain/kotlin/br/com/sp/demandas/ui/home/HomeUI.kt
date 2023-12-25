package br.com.sp.demandas.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BadgedBox
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.app.subtitle
import br.com.sp.demandas.core.app.title
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.demandas.DemandasUI
import br.com.sp.demandas.ui.mensagens.MensagemUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {

        val scaffoldState: ScaffoldState = rememberScaffoldState()
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val viewModel = getScreenModel<HomeViewModel>()
        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.uiState.collectAsState()

        viewModel.effect.observeAsActions {
            if (it is HomeScreenContract.Effect.ShowSnackbar) {

                when (it.throwable) {
                    is ClientException -> {
                        snackbarType.value = (SnackbarType.ALERT)
                        println(snackbarType)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "Erro no App",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }

                    is ServerException -> {

                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ERROR)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }

                    is UnknownException -> {

                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ERROR)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                it.throwable.message.toString(),
                                "Erro indefinido",
                                SnackbarDuration.Indefinite
                            )
                        }
                    }
                }
            }
            if (it is HomeScreenContract.Effect.GoTo) {
                navigator.push(it.screen)
            }
        }
        Scaffold(
            topBar = {
                MaxTopAppBarCenter(title = {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(MR.images.sp_mini),
                        contentDescription = "logo"
                    )

                }, navigationIcon = {}, actions = {
                    IconButton(onClick = {
                        navigator.popUntilRoot()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Sair do sistema"

                        )
                    }
                })
            },
            snackbarHost = {
                SnackbarHost(scaffoldState.snackbarHostState) {
                    Snackbar(it.actionLabel ?: "", it.message, snackbarType.value) {
                        it.dismiss()
                    }
                }
            }) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                /*androidx.compose.material3.Text(
                    text = "Convênios",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.displayMedium
                )
                androidx.compose.material3.Text(
                    text = "Subsecretaria de Convênios com Municípios e Entidades não Governamentais",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal
                    ),
                )*/

                Column(modifier = Modifier.fillMaxWidth()) {

                    Spacer(Modifier.height(16.dp))

                    Column(
                        Modifier.fillMaxWidth().background(Color.LightGray)
                            .padding(vertical = 12.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                        )
                        androidx.compose.material3.Text(
                            text = subtitle,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            ),
                        )
                    }

                }


                Spacer(Modifier.height(35.dp))
                androidx.compose.material3.Text(
                    text = "Aplicativo Convênios SGRI",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                    verticalArrangement = Arrangement.Top,
                    maxItemsInEachRow = 2
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0e5a94),
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(16.dp, 5.dp, 8.dp, 10.dp).fillMaxWidth(0.43f)
                            .clickable {
                                viewModel.setEvent(HomeScreenContract.Event.GoTo(DemandasUI()))
                            },
                    ) {
                        Column(
                            Modifier.padding(top = 16.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(55.dp)
                                    .fillMaxWidth(),
                                painter = painterResource(MR.images.obra),
                                contentDescription = "demanda_icone",
                            )

                            //TODO(): https://freeicons.io/icon-list/free,-flat-line-ecommerce-icon-pack
                            Column(modifier = Modifier.padding(16.dp)) {
                                androidx.compose.material3.Text(
                                    text = "Demandas",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }




                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0e5a94),
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(16.dp, 5.dp, 0.dp, 10.dp).fillMaxWidth(0.43f)
                            .clickable {
                                viewModel.setEvent(HomeScreenContract.Event.GoTo(MensagemUI()))
                            },
                    ) {

                            Column(
                                Modifier.fillMaxWidth().padding(top = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                BadgedBox(badge = {
                                    if (state.state is ResourceUiState.Success) {
                                        val mensagens =
                                            (state.state as ResourceUiState.Success<List<Mensagem>>).data
                                        if (mensagens.isNotEmpty())
                                            Badge {
                                                Text(
                                                    text = mensagens.count().coerceAtMost(999).toString(),
                                                    style = TextStyle(
                                                        color = Color.White
                                                    ),
                                                    modifier = Modifier.padding(2.dp)
                                                )
                                            }
                                    }
                                }) {
                                    Image(
                                        modifier = Modifier
                                            .height(55.dp),
                                        painter = painterResource(MR.images.mensagem),
                                        contentDescription = "demanda_icone",
                                    )
                                }

                                //TODO(): https://freeicons.io/icon-list/free,-flat-line-ecommerce-icon-pack
                                Column(modifier = Modifier.padding(16.dp)) {
                                    androidx.compose.material3.Text(
                                        text = "Mensagens",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 0.dp)
                                            .fillMaxWidth(),
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                            }
                        }

                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(16.dp, 5.dp, 8.dp, 10.dp).fillMaxWidth(0.43f),
                    ) {
                        Column(
                            Modifier.padding(top = 16.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(55.dp)
                                    .fillMaxWidth(),
                                painter = painterResource(MR.images.checklist),
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = "demanda_icone",
                            )

                            //TODO(): https://freeicons.io/icon-list/free,-flat-line-ecommerce-icon-pack
                            Column(modifier = Modifier.padding(16.dp)) {
                                androidx.compose.material3.Text(
                                    text = "Vistoria",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(16.dp, 5.dp, 0.dp, 10.dp).fillMaxWidth(0.43f),
                    ) {
                        Column(
                            Modifier.padding(top = 16.dp)
                        ) {

                            Image(
                                modifier = Modifier
                                    .height(55.dp)
                                    .fillMaxWidth(),
                                painter = painterResource(MR.images.fotos),
                                contentDescription = "demanda_icone",
                            )

                            //TODO(): https://freeicons.io/icon-list/free,-flat-line-ecommerce-icon-pack
                            Column(modifier = Modifier.padding(16.dp)) {
                                androidx.compose.material3.Text(
                                    text = "Fotos",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(16.dp, 5.dp, 0.dp, 10.dp).fillMaxWidth(0.43f),
                    ) {
                        Column(
                            Modifier.padding(top = 16.dp)
                        ) {

                            Image(
                                modifier = Modifier
                                    .height(55.dp)
                                    .fillMaxWidth(),
                                painter = painterResource(MR.images.dashboard),
                                contentDescription = "demanda_icone",
                            )

                            //TODO(): https://freeicons.io/ecommerce-icon-set-11/dashboard-chart-graph-icon-279917
                            Column(modifier = Modifier.padding(16.dp)) {
                                androidx.compose.material3.Text(
                                    text = "Visão gerencial",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 0.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
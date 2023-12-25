package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.MaxTopAppBarLeftTitle
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.design.components.Table
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.demandas.Responsabilidade
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

class DetalheDemandaScreen(private val demandaId: String) : Screen {

    override val key: ScreenKey
        get() = demandaId

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {


        val viewModel = getScreenModel<DetalheDemandaViewModel> { parametersOf(demandaId) }

        val state by viewModel.uiState.collectAsState()

        val scaffoldState = rememberScaffoldState()
        val modalBottomSheetState =
            rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
        val navigator = LocalNavigator.currentOrThrow

        val scope = rememberCoroutineScope()

        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }

        viewModel.effect.observeAsActions {
            if (it is DetalheDemandaContract.Effect.ShowSnackbar) {

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

                    else -> {
                        println(snackbarType)
                        snackbarType.value = (SnackbarType.ALERT)
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar(
                                "Atenção",
                                it.throwable.message.toString(),
                                SnackbarDuration.Indefinite
                            )
                        }
                    }
                }
            }


            if (it is DetalheDemandaContract.Effect.GoToParcelas) {
                navigator.push(DetalheDemandaScreen(it.id))
            }
            if (it is DetalheDemandaContract.Effect.ShowEtapas) {

                scope.launch {
                    modalBottomSheetState.show()
                }
            }
            if(it is DetalheDemandaContract.Effect.ShowObs){
            }
        }

        ModalBottomSheetLayout(sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(16.dp),
            sheetGesturesEnabled = true,
            sheetContent = {
                EtapaDetalheBottomSheet(
                    viewModel,
                    modalBottomSheetState.currentValue,
                    modalBottomSheetState.progress
                ) {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            }) {

            TabNavigator(DetalheTab(viewModel)) {
                Scaffold(
                    topBar = {
                        MaxTopAppBarCenter(title = {

                            if (state.state is ResourceUiState.Success)
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append("${if ((state.state as ResourceUiState.Success<DemandaDetalhe>).data.idAtividadePai == null) "Demanda" else "Parcela"}: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal
                                            )
                                        ) {
                                            append((state.state as ResourceUiState.Success<DemandaDetalhe>).data.numero)
                                        }

                                    },
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

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
                    bottomBar = {

                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ) {

                            if (state.state is ResourceUiState.Success)
                                if ((state.state as ResourceUiState.Success<DemandaDetalhe>).data.idAtividadePai == null) {

                                    TabNavigationItem(it, DetalheTab(viewModel))
                                    TabNavigationItem(it, EtapasTab(
                                        viewModel,
                                        onClickEtapa = { etapa ->
                                            viewModel.handleEvent(
                                                DetalheDemandaContract.Event.OpenEtapas(
                                                    etapa
                                                )
                                            )
                                        }
                                    ) {

                                    })
                                    TabNavigationItem(it, ParcelasTab(viewModel))
                                    TabNavigationItem(it, EventosTab(viewModel))
                                } else {
                                    TabNavigationItem(it, DetalheTab(viewModel))
                                    TabNavigationItem(it, EventosTab(viewModel))
                                }

                        }
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

                        CurrentTab()

                    }
                }
            }

        }

    }
}


@Composable
private fun RowScope.TabNavigationItem(tabNavigator: TabNavigator, tab: Tab) {


    NavigationBarItem(
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title
                )
            }
        },
        label = { Text(tab.options.title) },
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalAnimationApi::class
)
@Composable
fun EtapaDetalheBottomSheet(
    viewModel: DetalheDemandaViewModel,
    bottomSheetValue: ModalBottomSheetValue,
    bottomSheetProgress: Float,
    hideBottomSheet: () -> Unit
) {


    val state by viewModel.uiState.collectAsState()
    when (state.state) {
        is ResourceUiState.Loading -> {
            Column(Modifier.fillMaxWidth()) {
                ShimmerPreview(8)
            }

        }

        is ResourceUiState.Empty -> {}
        is ResourceUiState.Error -> {}
        ResourceUiState.Idle -> {}
        is ResourceUiState.Success -> {
            val item = viewModel.uiState.value.demanda
            Scaffold(
                topBar = {
                    MaxTopAppBarLeftTitle(modifier = Modifier.animateContentSize(
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
                        windowInsets = if (bottomSheetValue == ModalBottomSheetValue.HalfExpanded) WindowInsets.captionBar else WindowInsets.statusBars,
                        title = {
                            Row(
                                modifier = Modifier.padding(start = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = item?.etapa ?: "",
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(
                                            item?.cor?.removePrefix(
                                                "#"
                                            )?.toLong(16)?.or(0x00000000FF000000)
                                                ?: 0xFFFFFFF
                                        ),
                                        fontWeight = FontWeight.Black
                                    ),
                                )
                            }

                        },
                        actions = {
                            IconButton(onClick = {
                                hideBottomSheet()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Sair do sistema"

                                )
                            }
                        })

                },
            ) {
                LazyColumn(Modifier.fillMaxWidth().padding(it)) {

                    item {

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            modifier = Modifier.padding(
                                16.dp, 5.dp, 16.dp, 10.dp
                            ).fillMaxWidth(1f),
                        ) {
                            Text(
                                text = "Resumo da Etapa",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(8.dp).background(
                                    color = Color(
                                        item?.cor?.removePrefix(
                                            "#"
                                        )?.toLong(16)?.or(0x00000000FF000000)
                                            ?: 0xFFFFFFF
                                    ), shape = RoundedCornerShape(4.dp)
                                ).padding(8.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.background,
                                    fontWeight = FontWeight.Black
                                ),
                            )

                            Card(
                                modifier = Modifier.padding(0.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Início: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(item?.dataInicio)
                                            }

                                        },
                                        textAlign = TextAlign.Start,
                                    )

                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Fim: ")
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Normal
                                                )
                                            ) {
                                                append(item?.dataFim)
                                            }

                                        },
                                        textAlign = TextAlign.Start,
                                    )
                                }


                                var people = item?.responsabilidade
                                people = people?.plus(
                                    Responsabilidade(
                                        "Total",
                                        "",
                                        item?.totalPrazoPrevisto ?: 0,
                                        item?.totalPrazoRealizado ?: 0,
                                        item?.totalPrazoVencido ?: 0
                                    )
                                )

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
                                val cellText: @Composable (Int, Responsabilidade) -> Unit =
                                    { index, item ->
                                        val value = when (index) {
                                            0 -> item.nome
                                            1 -> item.prazoPrevisto
                                            2 -> item.prazoRealizado
                                            3 -> item.prazoVencido
                                            else -> ""
                                        }

                                        Text(
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
                                    }


                                Table(
                                    columnCount = 4,
                                    cellWidth = cellWidth,
                                    data = people ?: emptyList(),
                                    headerCellContent = headerCellTitle,
                                    cellContent = cellText
                                )

                                Spacer(Modifier.height(16.dp))
                            }

                        }

                        Text(
                            text = "Tramitações da etapa",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Black
                            ),
                        )

                        item?.historico?.forEach { historico ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                modifier = Modifier.padding(
                                    16.dp, 5.dp, 16.dp, 10.dp
                                ).fillMaxWidth(1f),
                            ) {
                                Column(
                                    Modifier.padding(16.dp).fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            ) {
                                                append("Situação: ")
                                            }

                                            append(historico.situacao)

                                        },
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Normal
                                        ),
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
                                                append("Data Inicio: ")
                                            }

                                            append(historico.dataInicio)

                                        },
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Normal
                                        ),

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
                                                append("Data Fim: ")
                                            }

                                            append(historico.dataFim)

                                        },
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Normal
                                        ),
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
                                                append("Duração em dias: ")
                                            }

                                            append(historico.prazoDias.toString())

                                        },
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Normal
                                        ),
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ObservacaoBottomSheet(
    bottomSheetValue: ModalBottomSheetValue,
    bottomSheetProgress: Float,
    hideBottomSheet: () -> Unit) {
    Scaffold(
        topBar = {
            MaxTopAppBarLeftTitle(modifier = Modifier.animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
                windowInsets = if (bottomSheetValue == ModalBottomSheetValue.HalfExpanded) WindowInsets.captionBar else WindowInsets.statusBars,
                title = {
                    Text(
                        text = "Observação",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                        ),
                    )

                },
                actions = {
                    IconButton(onClick = {
                        hideBottomSheet()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Sair do sistema"

                        )
                    }
                })

        },
    ) {
        Text("OBS:\n" +
                "1.O Prazo objetivo define em dias o desempenho esperado para o progresso de realização do CONVÊNIO.\n" +
                "2.O CVC - CICLO DE VIDA DO CONVÊNIO estabelece à partir da data início a duração de cada ETAPA desde a inclusão até o fechamento.\n" +
                "3.O prazo real é contado em cada ETAPA e comparado ao objetivo sendo apresentado por responsabilidades.\n" +
                "4.Cada ETAPA possui uma cor específica e é utilizada para sua identificação tem um circulo vermelho nas demandas com prazo vencido")
    }
}

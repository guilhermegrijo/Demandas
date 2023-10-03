package br.com.sp.demandas.ui.demandas

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.Accordion
import br.com.sp.demandas.design.components.MaxOutlinedTextField
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.MaxTopAppBarLeftTitle
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.filtroDemanda.model.DemandaState
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import br.com.sp.demandas.domain.filtroDemanda.model.FiltroState
import br.com.sp.demandas.ui.detalheDemanda.DetalheDemandaScreen
import br.com.sp.demandas.ui.login.checkCode.CheckCodeUI
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

class DemandasUI : Screen {
    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalComposeUiApi::class,
        ExperimentalMaterialApi::class
    )
    @Composable
    override fun Content() {

        val pesquisa = remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current

        val scaffoldState = rememberScaffoldState()
        val modalBottomSheetState =
            rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        val scope = rememberCoroutineScope()

        var hasOpen by remember { mutableStateOf(false) }

        val viewModel = getScreenModel<DemandaViewModel>()
        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }
        var itemCount by remember { mutableStateOf(15) }


        val refreshState = rememberPullRefreshState(refreshing,
            { viewModel.handleEvent(DemandaScreenContract.Event.Refresh) })

        val state by viewModel.uiState.collectAsState()

        viewModel.effect.observeAsActions {
            if (it is DemandaScreenContract.Effect.ShowSnackbar) {

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
                                "Erro no servidor",
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


            if (it is DemandaScreenContract.Effect.NavigateToDetail) {
                navigator.push(DetalheDemandaScreen(it.id))
            }
        }

        LaunchedEffect(true) {
            if (!hasOpen)
                scope.launch {
                    modalBottomSheetState.apply {
                        show()
                    }
                    hasOpen = true
                    print(navigator.lastEvent)

                }
        }

        ModalBottomSheetLayout(sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(16.dp),
            sheetGesturesEnabled = true,
            sheetContent = {
                FilterBottomSheet(viewModel, modalBottomSheetState.currentValue) {

                    scope.launch { modalBottomSheetState.hide() }
                }
            }) {

            Scaffold(
                topBar = {
                    MaxTopAppBarCenter(title = {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.weight(0.7f),
                                painter = painterResource(MR.images.govsp),
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
                        IconButton(onClick = {
                            scope.launch {
                                modalBottomSheetState.apply {
                                    if (isVisible) hide() else show()
                                }
                            }
                        }) {
                            Icon(
                                painter = painterResource(MR.images.filter_alt),
                                contentDescription = "user_passowrd",
                                tint = Color.Gray
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
                },
            ) {


                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background).padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    androidx.compose.material3.Text(
                        text = "Demandas",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Black
                        ),
                    )


                    MaxOutlinedTextField(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        value = pesquisa.value,
                        label = {
                            Text(
                                text = "Pesquisar número da demanda",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        enabled = true,
                        isError = false,
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                        keyboardActions = KeyboardActions {
                            keyboardController?.hide()
                        },
                        textError = "",
                        onValueChange = { pesquisa.value = it })



                    when (state.state) {
                        is ResourceUiState.Loading -> {
                            ShimmerPreview(8)
                            refreshing = true
                        }


                        is ResourceUiState.Empty -> {

                            refreshing = false
                        }

                        is ResourceUiState.Error -> {}

                        is ResourceUiState.Idle -> {}

                        is ResourceUiState.Success -> {
                            refreshing = false

                            if (state.filtroState.regionalFiltro?.isEmpty() == true && (state.state as ResourceUiState.Success<DemandaState>).data.lista?.isEmpty() == true) Column(
                                Modifier.fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null, // decorative
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.padding(top = 35.dp).height(48.dp)
                                        .fillMaxWidth(),

                                    )

                                Divider(
                                    modifier = Modifier.fillMaxWidth().height(1.dp).padding(16.dp)
                                        .background(Color.LightGray)
                                )
                                Column(modifier = Modifier.padding(16.dp)) {
                                    androidx.compose.material3.Text(
                                        text = "Filtro atual sem demandas",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 5.dp).fillMaxWidth(),
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                            }
                            else LazyColumn(Modifier.fillMaxSize()) {
                                if (state.state is ResourceUiState.Success) items(items = (state.state as ResourceUiState.Success<DemandaState>).data.lista?.filter {
                                    it.numeroDemanda.contains(
                                        pesquisa.value, ignoreCase = true
                                    )
                                } ?: emptyList()) {
                                    Card(
                                        shape = RoundedCornerShape(16.dp),
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
                                                DemandaScreenContract.Event.NavigateToDetail(
                                                    it.idAtividade
                                                )
                                            )
                                        },
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp)
                                        ) {
                                            Column() {

                                                androidx.compose.material3.Text(
                                                    text = buildAnnotatedString {
                                                        append("Demanda: ")


                                                        append(it.numeroDemanda)

                                                        append("\n")

                                                        append(it.etapa)

                                                    },
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.drawBehind {
                                                        drawRoundRect(
                                                            Color(
                                                                it.situacaoColor.removePrefix(
                                                                    "#"
                                                                ).toLong(16) or 0x00000000FF000000
                                                            ),
                                                            cornerRadius = CornerRadius(10.dp.toPx())
                                                        )
                                                    }.padding(8.dp).fillMaxWidth(),
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        color = MaterialTheme.colorScheme.onSecondary
                                                    ),
                                                )
                                                Spacer(modifier = Modifier.height(16.dp))

                                            }

                                            Column(
                                            ) {
                                                androidx.compose.material3.Text(
                                                    text = buildAnnotatedString {
                                                        withStyle(
                                                            style = SpanStyle(
                                                                color = MaterialTheme.colorScheme.onBackground,
                                                                fontWeight = FontWeight.SemiBold
                                                            )
                                                        ) {
                                                            append("Portfólio: ")
                                                        }

                                                        append(it.portfolio)

                                                    },
                                                    textAlign = TextAlign.Start,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Normal
                                                    ),
                                                )

                                                androidx.compose.material3.Text(
                                                    text = buildAnnotatedString {
                                                        withStyle(
                                                            style = SpanStyle(
                                                                color = MaterialTheme.colorScheme.onBackground,
                                                                fontWeight = FontWeight.SemiBold
                                                            )
                                                        ) {
                                                            append("Valor total: ")
                                                        }

                                                        append(it.valorTotal)

                                                    },
                                                    textAlign = TextAlign.Start,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Normal
                                                    ),
                                                )
                                                androidx.compose.material3.Text(
                                                    text = buildAnnotatedString {
                                                        withStyle(
                                                            style = SpanStyle(
                                                                fontWeight = FontWeight.SemiBold
                                                            )
                                                        ) {
                                                            append("Valor do Estado: ")
                                                        }

                                                        append(it.valorEstado)

                                                    },
                                                    textAlign = TextAlign.Start,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Normal
                                                    ),
                                                )
                                            }
                                            Text(
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
                                                            fontWeight = FontWeight.Normal
                                                        )
                                                    ) {
                                                        append(it.situacao)
                                                    }


                                                },
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.fillMaxWidth()
                                            )


                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(vertical = 8.dp).height(48.dp),
                                                horizontalArrangement = Arrangement.SpaceEvenly,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                androidx.compose.material3.Text(
                                                    text = "Aviso:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.padding(end = 5.dp),
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                )

                                                Text(
                                                    text = it.aviso,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .background(
                                                            Color(0xFFF8D355),
                                                            shape = CircleShape
                                                        )
                                                        .circleLayout()
                                                        .padding(8.dp)
                                                )

                                                androidx.compose.material3.Text(
                                                    text = "Alertas:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.padding(end = 5.dp),
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                )


                                                Text(
                                                    text = it.alerta,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .background(
                                                            Color(0xFFF9B859),
                                                            shape = CircleShape
                                                        )
                                                        .circleLayout()
                                                        .padding(8.dp)
                                                )

                                                androidx.compose.material3.Text(
                                                    text = "Alarme:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.padding(end = 5.dp),
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                )


                                                Text(
                                                    text = it.alarme,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .background(
                                                            Color(0xFFF96A59),
                                                            shape = CircleShape
                                                        )
                                                        .circleLayout()
                                                        .padding(8.dp)
                                                )

                                            }

                                            Spacer(Modifier.height(4.dp))
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
}

fun Modifier.circleLayout() =
    layout { measurable, constraints ->
        // Measure the composable
        val placeable = measurable.measure(constraints)

        //get the current max dimension to assign width=height
        val currentHeight = placeable.height
        val currentWidth = placeable.width
        val newDiameter = maxOf(currentHeight, currentWidth)

        //assign the dimension and the center position
        layout(newDiameter, newDiameter) {
            // Where the composable gets placed
            placeable.placeRelative(
                (newDiameter - currentWidth) / 2,
                (newDiameter - currentHeight) / 2
            )
        }
    }


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SegmentButton(
    modifier: Modifier,
    itemsList: List<String>,
    selectedIndexes: List<String>,
    onOptionSelect: (String) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3
    ) {

        val cornerRadius = 16.dp

        itemsList.forEachIndexed { index, item ->

            OutlinedButton(
                onClick = { onOptionSelect(item) },
                colors = if (selectedIndexes.contains(item)) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                },
                modifier = when (index) {
                    0 -> Modifier.offset(0.dp, 0.dp)
                        .zIndex(if (selectedIndexes.contains(item)) 1f else 0f)

                    else -> Modifier.offset((-1 * index).dp, 0.dp)
                        .zIndex(if (selectedIndexes.contains(item)) 1f else 0f)

                }.height(56.dp).weight(1f / itemsList.size),
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )

                    2 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )

                    3 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = cornerRadius,
                        bottomStart = cornerRadius,
                        bottomEnd = cornerRadius
                    )

                    else -> RoundedCornerShape(
                        topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    0.dp, MaterialTheme.colorScheme.background
                ),

                ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selectedIndexes.contains(item)) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onBackground

                    }
                )
            }
        }
    }
}
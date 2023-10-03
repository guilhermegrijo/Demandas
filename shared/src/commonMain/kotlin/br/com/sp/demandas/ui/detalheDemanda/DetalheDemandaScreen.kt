package br.com.sp.demandas.ui.detalheDemanda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ClientException
import br.com.sp.demandas.core.ServerException
import br.com.sp.demandas.core.UnknownException
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.core.ui.getScreenModel
import br.com.sp.demandas.design.components.MaxTopAppBarCenter
import br.com.sp.demandas.design.components.Snackbar
import br.com.sp.demandas.design.components.SnackbarType
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.ui.demandas.DemandaScreenContract
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
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
        val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
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


            if (it is DetalheDemandaContract.Effect.GoToParcelas) {
                navigator.push(DetalheDemandaScreen(it.id))
            }
        }

        ModalBottomSheetLayout(sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(16.dp),
            sheetGesturesEnabled = true,
            sheetContent = {
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
                                                append("${if((state.state as ResourceUiState.Success<DemandaDetalhe>).data.idAtividadePai == null) "Demanda" else "Parcela"}: ")
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

                                    TabNavigationItem(DetalheTab(viewModel))
                                    TabNavigationItem(ParcelasTab(viewModel))
                                    TabNavigationItem(EtapasTab(viewModel))
                                    TabNavigationItem(EventosTab(viewModel))
                                } else {
                                    TabNavigationItem(DetalheTab(viewModel))
                                    TabNavigationItem(EventosTab(viewModel))
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
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

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
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
    )
}
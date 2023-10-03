package br.com.sp.demandas.ui.demandas

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import br.com.sp.demandas.MR
import br.com.sp.demandas.core.ui.ResourceUiState
import br.com.sp.demandas.design.components.Accordion
import br.com.sp.demandas.design.components.AutoComplete
import br.com.sp.demandas.design.components.MaxButton
import br.com.sp.demandas.design.components.MaxTopAppBarLeftTitle
import br.com.sp.demandas.design.components.ShimmerPreview
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import br.com.sp.demandas.domain.filtroDemanda.model.FiltroState
import br.com.sp.demandas.domain.filtroDemanda.model.TipoAcesso
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalAnimationApi::class
)
@Composable
fun FilterBottomSheet(
    viewModel: DemandaViewModel,
    bottomSheetValue: ModalBottomSheetValue,
    hideBottomSheet: () -> Unit
) {


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
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(MR.images.filter_alt),
                            contentDescription = "user_passowrd",
                            tint = Color.Gray
                        )
                        Spacer(Modifier.width(12.dp))
                        Text("Filtro")
                        Image(
                            modifier = Modifier.height(70.dp),
                            painter = painterResource(MR.images.govsp),
                            contentDescription = "logo"
                        )
                        Text("SGRI", fontWeight = FontWeight.SemiBold)
                    }

                },
                actions = {
                    IconButton(onClick = {
                        hideBottomSheet()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close, contentDescription = "Sair do sistema"

                        )
                    }
                })

        },
    ) {
        val uiState by viewModel.uiState.collectAsState()
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {

                Column(modifier = Modifier.fillMaxWidth()) {
                    /*val logo =
                        if (isSystemInDarkTheme()) MR.images.govsp else MR.images.logosp_branco

                    Image(
                        modifier = Modifier.fillMaxWidth(0.7f).background(
                            Color.Black, RoundedCornerShape(
                                topEnd = 16.dp,
                                bottomEnd = 16.dp
                            )
                        ),
                        painter = painterResource(logo),
                        contentDescription = "Localized_Logo"
                    )*/

                    Column(modifier = Modifier.fillMaxWidth()) {

                        Spacer(Modifier.height(16.dp))

                        Column(
                            Modifier.fillMaxWidth().background(Color.LightGray)
                                .padding(vertical = 12.dp)
                        ) {
                            androidx.compose.material3.Text(
                                text = "SGRI - SECRETARIA DE GOVERNO E RELACIONAMENTO INSTITUCIONAL",
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                ),
                            )
                            androidx.compose.material3.Text(
                                text = "SUBSECRETARIA DE CONVÊNIOS COM MUNICÍPIOS E ENTIDADES NÃO GOVERNAMENTAIS",
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

                }

                when (uiState.state) {
                    is ResourceUiState.Loading -> {
                        ShimmerPreview(8)
                    }

                    is ResourceUiState.Empty -> {
                    }

                    is ResourceUiState.Error -> {
                    }

                    else -> {
                        var numeroDemanda by rememberSaveable {
                            mutableStateOf(uiState.filtroState.numeroDemandaFiltro ?: "")
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        ) {

                            androidx.compose.material3.Text(
                                modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                                text = "Número da Demanda",
                                fontSize = 16.sp,
                            )

                            Column(modifier = Modifier.fillMaxWidth()) {

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(55.dp),
                                        value = numeroDemanda,
                                        onValueChange = {
                                            numeroDemanda = it
                                            viewModel.handleEvent(
                                                DemandaScreenContract.Event.FiltroNumeroDemanda(
                                                    it
                                                )
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            disabledContainerColor = MaterialTheme.colorScheme.background,
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true,
                                    )
                                }
                            }
                        }





                        if ((uiState.state as ResourceUiState.Success).data.tipoAcesso == TipoAcesso.TOTAL)
                            AutoComplete(
                                titulo = "Regional",
                                filtro = uiState.filtroState.regionalFiltro ?: "",
                                list = uiState.filtroState.filtroHierarquiaRegional
                            ) {
                                viewModel.handleEvent(DemandaScreenContract.Event.FiltroRegional(it))
                            }
                        else if((uiState.state as ResourceUiState.Success).data.tipoAcesso == TipoAcesso.REGIONAL)
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                            ) {

                                androidx.compose.material3.Text(
                                    modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                                    text = "Regional",
                                    fontSize = 16.sp,
                                )

                                Column(modifier = Modifier.fillMaxWidth()) {

                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(55.dp),
                                            value = uiState.filtroState.filtroHierarquiaRegional.firstOrNull()?.texto ?: "",
                                            onValueChange = {
                                            },
                                            enabled = false,
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                disabledContainerColor = MaterialTheme.colorScheme.background,
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Done
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            singleLine = true,
                                        )
                                    }
                                }
                            }

                        if (uiState.filtroState.filtroPrefeituras.isNotEmpty()) {

                            if((uiState.state as ResourceUiState.Success).data.tipoAcesso == TipoAcesso.REGIONAL || (uiState.state as ResourceUiState.Success).data.tipoAcesso == TipoAcesso.TOTAL)
                            AutoComplete(
                                titulo = "Prefeitura",
                                filtro = uiState.filtroState.prefeituraFiltro ?: "",
                                list = uiState.filtroState.filtroPrefeituras,
                            ) {
                                viewModel.handleEvent(
                                    DemandaScreenContract.Event.FiltroPrefeitura(
                                        it
                                    )
                                )
                            }
                            if (uiState.filtroState.filtroPrefeituras.isNotEmpty() && (uiState.state as ResourceUiState.Success).data.tipoAcesso == TipoAcesso.PREFEITURA) {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {

                                    androidx.compose.material3.Text(
                                        modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
                                        text = "Prefeitura",
                                        fontSize = 16.sp,
                                    )

                                    Column(modifier = Modifier.fillMaxWidth()) {

                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            OutlinedTextField(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(55.dp),
                                                value = uiState.filtroState.filtroPrefeituras.firstOrNull()?.texto
                                                    ?: "",
                                                onValueChange = {
                                                },
                                                enabled = false,
                                                colors = TextFieldDefaults.colors(
                                                    focusedContainerColor = Color.Transparent,
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                                    disabledContainerColor = MaterialTheme.colorScheme.background,
                                                ),
                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Text,
                                                    imeAction = ImeAction.Done
                                                ),
                                                shape = RoundedCornerShape(8.dp),
                                                singleLine = true,
                                            )
                                        }
                                    }
                                }
                            }

                            AutoComplete(
                                titulo = "Etapa",
                                filtro = uiState.filtroState.etapaFiltro ?: "",
                                uiState.filtroState.comboEtapa,
                            ) {
                                viewModel.handleEvent(DemandaScreenContract.Event.FiltroEtapa(it))
                            }

                            AutoComplete(
                                titulo = "Situação",
                                filtro = uiState.filtroState.statusFiltro ?: "",
                                uiState.filtroState.comboStatus,
                            ) {
                                viewModel.handleEvent(DemandaScreenContract.Event.FiltroStatus(it))
                            }
                            AutoComplete(
                                titulo = "Convênio",
                                filtro = uiState.filtroState.convenioFiltro ?: "",
                                uiState.filtroState.comboConvenio,
                            ) {
                                viewModel.handleEvent(DemandaScreenContract.Event.FiltroConvenio(it))
                            }
                            var selectedIndexes by remember {
                                mutableStateOf(uiState.filtroState.filtroSelect)
                            }

                            val itemsList = listOf("Aviso", "Alerta", "Aviso", "No prazo")

                            SegmentButton(
                                modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
                                itemsList = itemsList,
                                selectedIndexes = selectedIndexes
                            ) {
                                val key = it

                                selectedIndexes = if (!selectedIndexes.contains(key)) {
                                    val list = selectedIndexes.toMutableList()
                                    if (key == "No prazo")
                                        list.clear()
                                    else list.remove("No prazo")
                                    list.add(key)
                                    list.toList()
                                } else {
                                    val list = selectedIndexes.toMutableList()
                                    list.remove(key)
                                    list.toList()
                                }
                                viewModel.handleEvent(
                                    DemandaScreenContract.Event.FiltroSelect(
                                        selectedIndexes
                                    )
                                )
                            }

                        }

                        if (uiState.filtroState.prefeituraFiltro?.isNotEmpty() == true || numeroDemanda.isNotEmpty()) {
                            MaxButton(
                                modifier = Modifier.fillMaxWidth(0.9f).height(50.dp),
                                text = "Filtrar",
                                enabled = true,
                                onClick = {
                                    hideBottomSheet()
                                    viewModel.handleEvent(DemandaScreenContract.Event.FiltrarDemanda)
                                }
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                }


            }
        }
    }
}


@Composable
fun ComboFiltro(titulo: String, list: List<Filtro>, function: (Filtro) -> Unit) {
    Accordion(
        title = titulo, model = list
    ) { id ->
        function(id)
    }
}

@Composable
fun PrefeituraFiltro(filtroState: FiltroState, function: (Filtro) -> Unit) {
    Accordion(
        title = filtroState.prefeituraFiltro ?: "", model = filtroState.filtroPrefeituras
    ) { id ->
        function(id)
    }
}
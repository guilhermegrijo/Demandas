package br.com.sp.demandas.ui.mensagens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
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
import br.com.sp.demandas.domain.demandas.DemandaDetalhe
import br.com.sp.demandas.domain.mensagem.Mensagem
import br.com.sp.demandas.ui.demandas.DemandaScreenContract
import br.com.sp.demandas.ui.detalheDemanda.DetalheDemandaScreen
import br.com.sp.demandas.ui.detalheMensagem.DetalheMensagemUI
import br.com.sp.demandas.ui.home.HomeViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch

class MensagemUI : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {

        val scaffoldState: ScaffoldState = rememberScaffoldState()
        var isDialogOpen by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val pesquisa = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        val viewModel = getScreenModel<MensagemViewModel>()
        val snackbarType = remember { mutableStateOf(SnackbarType.INFO) }
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.uiState.collectAsState()

        viewModel.effect.observeAsActions {
            if (it is MensagemScreenContract.Effect.ShowSnackbar) {

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
            if (it is MensagemScreenContract.Effect.GoTo) {
                navigator.push(it.screen)
            }
        }

        Scaffold(
            topBar = {
                MaxTopAppBarCenter(title = {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier.size(70.dp),
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
                    text = "Mensagens",
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
                            text = "Pesquisar mensagem por número demanda",
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
                    }


                    is ResourceUiState.Empty -> {
                    }

                    is ResourceUiState.Error -> {}

                    is ResourceUiState.Idle -> {}

                    is ResourceUiState.Success -> {
                        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
                            items((state.state as ResourceUiState.Success<List<Mensagem>>).data.filter {
                                it.atividadeNumero.contains(
                                    pesquisa.value, ignoreCase = true
                                )
                            }) {
                                CardNotificacao(it) {
                                    viewModel.handleEvent(
                                        MensagemScreenContract.Event.GoTo(
                                            DetalheMensagemUI(it)
                                        )
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

@Composable
fun CardNotificacao(
    msg: Mensagem,
    onClick: (Mensagem) -> Unit

) {

    Message(
        msg = msg,
        onClick,

        )

}

@Composable
fun Message(
    msg: Mensagem,
    onClick: (Mensagem) -> Unit
) {
    Row(modifier = Modifier.padding(top = 8.dp).clickable { onClick(msg) }) {
/*        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                modifier = Modifier
                    .clickable(onClick = { onAuthorClick(msg.author) })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painterResource(id = msg.authorImage),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }*/
        AuthorAndTextMessage(
            msg = msg,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: Mensagem,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AuthorNameTimestamp(msg)

        ChatItemBubble(message = msg, isUserMe = false, authorClicked = {})
        DayHeader(dayString = " ")
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun AuthorNameTimestamp(msg: Mensagem) {
    // Combine author and timestamp for a11y.
    Column(modifier = Modifier
        .fillMaxWidth()
        .semantics(mergeDescendants = true) {}) {
        Text(
            text = "Demanda: ${msg.atividadeNumero}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Text(
            text = "Portfólio: ${msg.atividadePortifolio}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Data: ${msg.dataRegistro}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ChatItemBubble(
    message: Mensagem,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {


    val colors = MaterialTheme.colorScheme

    val backgroundBubbleColor =
        colors.onBackground

    Column(Modifier.fillMaxWidth()) {
        Surface(
            Modifier.fillMaxWidth(),
            color = backgroundBubbleColor,
            shape = ChatBubbleShape,
            border = BorderStroke(1.dp, colors.primary)
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
                authorClicked = authorClicked
            )
        }
    }
}

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Composable
fun ClickableMessage(
    message: Mensagem,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {

    Text(
        text = message.mensagem,
        style = MaterialTheme.typography.bodyMedium.copy(color = LocalContentColor.current),
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(16.dp),
    )
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

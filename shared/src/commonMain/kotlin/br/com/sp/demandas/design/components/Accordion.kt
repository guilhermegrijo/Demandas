package br.com.sp.demandas.design.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
private fun AccordionHeader(
    title: String = "Header",
    filter: String,
    onChange: (String) -> Unit,
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {},
) {
    val degrees = if (isExpanded) 180f else 0f

    var focus by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(value = if (title != filter && focus) filter else title,
            interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onTapped()
                            focus = !focus
                            if (focus) {
                                onChange("")
                            } else keyboardController?.hide()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onChange,
            label = { if (focus) Text(title) },
            keyboardActions = KeyboardActions.Default,
            trailingIcon = {
                Icon(
                    Icons.Outlined.ArrowDropDown,
                    contentDescription = "arrow-down",
                    modifier = Modifier.rotate(degrees),
                    tint = Gray
                )
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    title: String,
    model: List<Filtro>,
    selectedFilter: (Filtro) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var filter by remember { mutableStateOf(title) }

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val bringIntoViewRequest = BringIntoViewRequester()

    Column(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        AccordionHeader(title = title,
            isExpanded = expanded,
            filter = filter,
            onChange = {
                filter = it
            }) {
            expanded = !expanded
            scope.launch {
                if (expanded)
                    bringIntoViewRequest.bringIntoView()
            }
        }
        AnimatedVisibility(visible = expanded) {
            Surface(
                color = White,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, LightGray),
                shadowElevation = 1.dp,
                modifier = Modifier.padding(top = 8.dp).bringIntoViewRequester(bringIntoViewRequest)
            ) {
                LazyColumn(Modifier.height(300.dp)) {
                    items(model.filter { it.texto.contains(filter, ignoreCase = true) }) { row ->
                        AccordionRow(row) {
                            expanded = !expanded
                            selectedFilter.invoke(it)
                            filter = it.texto
                        }
                        Divider(color = LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}


@Composable
private fun AccordionRow(
    model: Filtro, selectedFilter: (Filtro) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp).clickable { selectedFilter.invoke(model) }
            .background(color = if (model.selected) LightGray else White),

        ) {
        Text(model.texto, Modifier.weight(1f))
    }
}
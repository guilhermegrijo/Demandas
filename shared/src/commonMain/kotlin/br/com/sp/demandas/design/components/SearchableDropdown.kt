package br.com.sp.demandas.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.domain.filtroDemanda.model.Filtro
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch

/**
 * 🚀 A Jetpack Compose Android Library to create a dropdown menu that is searchable.
 * @param modifier a modifier for this SearchableExpandedDropDownMenu and its children
 * @param listOfItems A list of objects that you want to display as a dropdown
 * @param enable controls the enabled state of the OutlinedTextField. When false, the text field will be neither editable nor focusable, the input of the text field will not be selectable, visually text field will appear in the disabled UI state
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty. The default text style for internal [Text] is [Typography.subtitle1]
 * @param openedIcon the Icon displayed when the dropdown is opened. Default Icon is [Icons.Outlined.KeyboardArrowUp]
 * @param closedIcon Icon displayed when the dropdown is closed. Default Icon is [Icons.Outlined.KeyboardArrowDown]
 * @param parentTextFieldCornerRadius : Defines the radius of the enclosing OutlinedTextField. Default Radius is 12.dp
 * @param colors [TextFieldColors] that will be used to resolve color of the text and content
 * (including label, placeholder, leading and trailing icons, border) for this text field in
 * different states. See [TextFieldDefaults.outlinedTextFieldColors]
 * @param onDropDownItemSelected Returns the item that was selected from the dropdown
 * @param dropdownItem Provide a composable that will be used to populate the dropdown and that takes a type i.e String,Int or even a custom type
 * @param showDefaultSelectedItem If set to true it will show the default selected item with the position of your preference, it's value is set to false by default
 * @param defaultItemIndex Pass the index of the item to be selected by default from the dropdown list. If you don't provide any the first item in the dropdown will be selected
 * @param defaultItem Returns the item selected by default from the dropdown list
 * @param onSearchTextFieldClicked use this if you are having problems with the keyboard showing, use this to show keyboard on your side
 */


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AutoComplete(titulo: String, filtro: String, list: List<Filtro>, function: (Filtro) -> Unit) {

    val categories = listOf(
        "Food",
        "Beverages",
        "Sports",
        "Learning",
        "Travel",
        "Rent",
        "Bills",
        "Fees",
        "Others",
    )

    var filter by rememberSaveable {
        mutableStateOf(filtro)
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val degrees = if (expanded) 180f else 0f
    val scope = rememberCoroutineScope()


    val bringIntoViewRequest = BringIntoViewRequester()

    // Category Field
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                    scope.launch {
                        bringIntoViewRequest.bringIntoView()
                    }
                }
            )
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = titulo,
            fontSize = 16.sp,
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = filter,
                    onValueChange = { selectedItem ->
                        filter = selectedItem
                        try {
                            val item = list.find { it.texto == selectedItem }
                            item?.let { function.invoke(item) }

                        } catch (ex: Exception) {
                            println(ex)
                        }
                        expanded = true
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
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                Icons.Outlined.ArrowDropDown,
                                contentDescription = "arrow-down",
                                modifier = Modifier.rotate(degrees),
                                tint = Color.Gray
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = expanded, modifier = Modifier.bringIntoViewRequester(bringIntoViewRequest)) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    elevation = 15.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {

                        if (filter.isNotEmpty()) {
                            items(
                                list.filter {
                                    it.texto.lowercase()
                                        .contains(filter.lowercase())
                                }.map{ it.copy(texto = it.texto.replace("\n", " ").replace("\r", " "))}.sortedBy { it.texto }
                            ) {
                                CategoryItems(title = it.texto) { selectedItem ->
                                    filter = selectedItem
                                    try {
                                        val item = list.find { it.texto == selectedItem }
                                        item?.let { function.invoke(item) }

                                    } catch (ex: Exception) {
                                        println(ex)
                                    }
                                    expanded = false
                                }
                            }
                        } else {
                            items(
                                list.map{ it.copy(texto = it.texto.replace("\n", " ").replace("\r", " "))}.sortedBy { it.texto }
                            ) {
                                CategoryItems(title = it.texto) { selectedItem ->
                                    filter = selectedItem
                                    try {
                                        val item = list.find { it.texto == selectedItem }
                                        item?.let { function.invoke(item) }

                                    } catch (ex: Exception) {
                                        println(ex)
                                    }
                                    expanded = false
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
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }

}
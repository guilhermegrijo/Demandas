package br.com.sp.demandas.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    titulo: String,
    descricao: String,
    imageResource: Painter,
    accepted: () -> Unit,
    openDialogCustom: (Boolean) -> Unit
) {
    AlertDialog(onDismissRequest = { openDialogCustom(false) }) {
        CustomDialogUI(
            titulo = titulo,
            descricao = descricao,
            imageResource = imageResource,
            accepted = accepted,
            openDialogCustom = openDialogCustom
        )
    }
}

//Layout
@Composable
fun CustomDialogUI(
    titulo: String,
    descricao: String,
    imageResource: Painter,
    modifier: Modifier = Modifier,
    accepted: () -> Unit,
    openDialogCustom: (Boolean) -> Unit
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
    ) {
        Column(
            modifier
                .background(MaterialTheme.colorScheme.background)
        ) {

            //.......................................................................
            Image(
                painter = imageResource,
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(48.dp)
                    .fillMaxWidth(),

                )

            Divider(
                modifier = Modifier.fillMaxWidth().height(1.dp).padding(16.dp)
                    .background(Color.LightGray)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = titulo,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    text = descricao,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    openDialogCustom(false)
                }) {

                    androidx.compose.material3.Text(
                        "Recusar",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = accepted) {
                    androidx.compose.material3.Text(
                        "Permitir",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}
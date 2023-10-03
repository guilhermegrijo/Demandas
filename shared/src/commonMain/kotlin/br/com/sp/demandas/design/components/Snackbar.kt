package br.com.sp.demandas.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.theme.MaxTheme

@Composable
fun Snackbar(actionLabel: String, message: String, snackbarType: SnackbarType, dismiss: () -> Unit) {
    println(snackbarType)
    MaxTheme {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            // modifier = modifier
            modifier = Modifier.padding(12.dp).fillMaxWidth(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Divider(
                    modifier = Modifier
                        .height(height = 80.dp)
                        .width(width = 6.dp),
                    color = when (snackbarType) {
                        SnackbarType.ALERT -> Color.Yellow
                        SnackbarType.ERROR -> Color.Red
                        SnackbarType.INFO -> Color.Green
                    }
                )
                Column(modifier = Modifier.padding(start = 12.dp, top = 10.dp, end = 12.dp))
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier,
                            text = actionLabel,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start,
                            )
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))

                        Icon(
                            modifier = Modifier.align(Alignment.Top).clickable { dismiss() }
                                .size(ButtonDefaults.IconSize),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 5.dp, top = 12.dp),
                            text = message,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                            ),
                            maxLines = 2, overflow = TextOverflow.Ellipsis
                        )
                    }

                }

            }

        }
    }
}

enum class SnackbarType {
    ALERT,
    ERROR,
    INFO
}
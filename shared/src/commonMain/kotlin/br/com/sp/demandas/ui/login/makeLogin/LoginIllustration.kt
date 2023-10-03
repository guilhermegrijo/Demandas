package br.com.sp.demandas.ui.login.makeLogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.getImageByFileName

@Composable
fun LoginIllustrator(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(),
    ) {

        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(MR.images.handshake),
            contentDescription = "login_illustrator",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "Convênios",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
            )
        )
    }
}

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EsqueceuSenhaTitleAndSubTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        TitleAndSubTitle()
    }
}

@Composable
private fun TitleAndSubTitle(
    modifier: Modifier = Modifier,
    title: String = "Esqueceu a senha?",
    subtitle: String = "Insira sua nova senha e a confirme"
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}
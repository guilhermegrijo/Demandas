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
    subtitle: String = "Para mudar de senha você precisa colocar o nome do seu usúario ou e-mail cadastrado na MAX e então enviaremos "+
            "um link ao seu e-mail para você redefinir sua senha."
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
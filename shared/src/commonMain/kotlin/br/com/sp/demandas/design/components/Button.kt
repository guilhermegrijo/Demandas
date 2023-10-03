package br.com.sp.demandas.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.theme.BaseShapes


@Composable
fun ButtonIconColumn(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(BorderStroke(width = 0.5.dp, borderColor), shape = MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = icon,
                contentDescription = "Localized Button",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


@Composable
fun ButtonIconRow(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(BorderStroke(width = 0.5.dp, color), shape = MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = color
                )
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = icon,
                contentDescription = "Localized Button",
                tint = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}

@Composable
fun ButtonIconBox(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    iconColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit,
) {
    Column(
        modifier
            .padding(2.dp)
            .clickable { if (enabled) onClick() else println("") },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor, shape = RoundedCornerShape(50))
                .height(85.dp)
                .width(85.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(50))

        ){
            Icon(
                modifier = Modifier
                    .padding(7.dp)
                    .align(Alignment.Center)
                    .width(55.dp)
                    .height(55.dp),
                imageVector = icon,
                contentDescription = "Localized icon",
                tint = iconColor
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ButtonIconFooterBox(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    iconColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit,
) {
    Column(
        modifier
            /*.padding(2.dp)*/
            .clickable { if(enabled) onClick() else println("") },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor, shape = RoundedCornerShape(50))
                .height(45.dp)
                .width(45.dp)
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(50))

        ){
            Icon(
                modifier = Modifier
                    .padding(7.dp)
                    .align(Alignment.Center)
                    .width(35.dp)
                    .height(35.dp),
                imageVector = icon,
                contentDescription = "Localized icon",
                tint = iconColor
            )
        }
    }
}




@Composable
fun MaxButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.inversePrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    MaxButtonCustom(
        modifier = modifier
            .border(
                width = 0.dp,
                Color.Transparent,
                shape = BaseShapes.small
            ),
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),

        ) {
        if (!enabled) CircularProgressIndicator(modifier = Modifier
            .size(35.dp)
            .padding(5.dp), color = MaterialTheme.colorScheme.onPrimary)
        else
            Text(
                text = text,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(5.dp),
            )
    }
}

@Composable
fun MaxRoundButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.inversePrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    MaxButtonCustom(
        modifier = modifier
            .border(
                width = 0.dp,
                Color.Transparent,
                shape = BaseShapes.small
            )
            .fillMaxWidth(),
        shape = BaseShapes.small,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        colors = ButtonDefaults.buttonColors()

    ) {
        if (!enabled) CircularProgressIndicator(modifier = Modifier
            .size(35.dp)
            .padding(5.dp), color = MaterialTheme.colorScheme.onPrimary)
        else
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(5.dp),
            )
    }
}

@Composable
private fun MaxButtonCustom(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content

    )
}
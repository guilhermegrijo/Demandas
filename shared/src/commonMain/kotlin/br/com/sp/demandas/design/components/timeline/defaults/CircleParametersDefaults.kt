package br.com.sp.demandas.design.components.timeline.defaults

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.sp.demandas.design.components.timeline.model.CircleParameters
import br.com.sp.demandas.design.components.timeline.model.StrokeParameters
import dev.icerock.moko.resources.ImageResource

object CircleParametersDefaults {

    private val defaultCircleRadius = 12.dp

    fun circleParameters(
        radius: Dp = defaultCircleRadius,
        backgroundColor: Color = Color.Cyan,
        stroke: StrokeParameters? = null,

        icon: ImageResource? = null
    ) = CircleParameters(
        radius,
        backgroundColor,
        stroke,
        icon
    )
}
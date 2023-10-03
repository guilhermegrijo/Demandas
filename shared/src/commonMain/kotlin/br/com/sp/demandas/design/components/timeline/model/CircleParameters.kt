package br.com.sp.demandas.design.components.timeline.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.icerock.moko.resources.ImageResource

data class CircleParameters(
    val radius: Dp,
    val backgroundColor: Color,
    val stroke: StrokeParameters? = null,
    val icon: ImageResource? = null
)
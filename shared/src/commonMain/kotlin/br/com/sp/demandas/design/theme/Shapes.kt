package br.com.sp.demandas.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val BaseShapes = Shapes(
    small = RoundedCornerShape(15.dp),
    medium = RoundedCornerShape(25.dp),
    large = RoundedCornerShape(0.dp)
)

val DefaultShape = Shapes(
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(5.dp),
    large = RoundedCornerShape(15.dp)
)

val BottomSheetShape = Shapes(
    medium = RoundedCornerShape(15.dp)
)

val CardShapes = Shapes(
    small = RoundedCornerShape(13.dp),
    medium = RoundedCornerShape(20.dp)
)
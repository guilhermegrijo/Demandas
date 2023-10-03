package br.com.sp.demandas.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import br.com.sp.demandas.MR
import dev.icerock.moko.resources.compose.asFont

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)



@Composable
fun MaxTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
/*    val fontFamily = FontFamily(
        MR.fonts.Montserrat.black.asFont(
            weight = FontWeight.W900,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.blackItalic.asFont(
            weight = FontWeight.W900,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.bold.asFont(
            weight = FontWeight.W700,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.boldItalic.asFont(
            weight = FontWeight.W700,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.extraBold.asFont(
            weight = FontWeight.ExtraBold,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.extraBoldItalic.asFont(
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.italic.asFont(
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.light.asFont(
            weight = FontWeight.Light,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.lightItalic.asFont(
            weight = FontWeight.Light,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.medium.asFont(
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.mediumItalic.asFont(
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.regular.asFont(
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.regular.asFont(
            weight = FontWeight.W400,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.semiBold.asFont(
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.semiBoldItalic.asFont(
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        )!!,
        MR.fonts.Montserrat.thin.asFont(
            weight = FontWeight.Thin,
            style = FontStyle.Normal
        )!!,
        MR.fonts.Montserrat.thinItalic.asFont(
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        )!!,


        )*/

    val defaultTextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )


    val sgc = Typography(
        displayLarge = defaultTextStyle.copy(

            lineBreak = LineBreak.Simple,
            fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp
        ),
        displayMedium = defaultTextStyle.copy(
            lineBreak = LineBreak.Simple,
            fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp
        ),
        displaySmall = defaultTextStyle.copy(
            lineBreak = LineBreak.Simple,
            fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
        ),
        headlineLarge = defaultTextStyle.copy(

            fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = 0.sp, lineBreak = LineBreak.Heading
        ),
        headlineMedium = defaultTextStyle.copy(

            fontSize = 28.sp, lineHeight = 36.sp, letterSpacing = 0.sp, lineBreak = LineBreak.Heading
        ),
        headlineSmall = defaultTextStyle.copy(

            fontSize = 24.sp, lineHeight = 32.sp, letterSpacing = 0.sp, lineBreak = LineBreak.Heading
        ),
        titleLarge = defaultTextStyle.copy(

            fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp, lineBreak = LineBreak.Heading
        ),
        titleMedium = defaultTextStyle.copy(

            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
            fontWeight = FontWeight.Medium,
            lineBreak = LineBreak.Heading
        ),
        titleSmall = defaultTextStyle.copy(

            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            fontWeight = FontWeight.Medium,
            lineBreak = LineBreak.Heading
        ),
        labelLarge = defaultTextStyle.copy(

            fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp, fontWeight = FontWeight.Medium
        ),
        labelMedium = defaultTextStyle.copy(

            fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp, fontWeight = FontWeight.Medium
        ),
        labelSmall = defaultTextStyle.copy(

            fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp, fontWeight = FontWeight.Medium
        ),
        bodyLarge = defaultTextStyle.copy(

            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            lineBreak = LineBreak.Paragraph
        ),
        bodyMedium = defaultTextStyle.copy(

            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
            lineBreak = LineBreak.Paragraph
        ),
        bodySmall = defaultTextStyle.copy(

            fontSize = 12.sp,
            lineHeight = 12.sp,
            letterSpacing = 0.4.sp,
            lineBreak = LineBreak.Paragraph
        ),
    )

    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        shapes = BaseShapes,
        typography = sgc
    )
}


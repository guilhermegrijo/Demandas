package br.com.sp.demandas.design.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTopAppBarNavigation(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    elevation: Dp = 0.dp
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized Icon",
                    modifier = Modifier.size(28.dp),
                )
            }
        },
        actions = actions,
    )
}


@Composable
fun MaxCenterTopAppBarNavigation(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    MaxCenterTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized Icon",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = actions,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        elevation = elevation
    )
}

@Composable
fun MenuTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    MaxCenterTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                ),
                color = textColor
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized Icon",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = actions,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        elevation = elevation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = textColor
            )
        },
        actions = actions,
    )
}

@Composable
fun MaxCenterTopAppBarCustom(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    MaxCenterTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        elevation = elevation
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTopAppBarCustom(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon ?: {},
        actions = actions,
    )
}

val AppBarHeight = 56.dp
val AppBarHorizontalPadding = 4.dp
var iconWidth = 72.dp - AppBarHorizontalPadding
var withoutIconWidth = 16.dp - AppBarHorizontalPadding

@Composable
private fun MaxLeftTitleTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
    val defLeftSectionWidth = if (navigationIcon == null) withoutIconWidth else iconWidth
    var leftSectionWidth by remember { mutableStateOf(defLeftSectionWidth) }
    var rightSectionWidth by remember { mutableStateOf(-1f) }
    var rightSectionPadding by remember { mutableStateOf(0f) }

    MaxAppBar(
        backgroundColor,
        contentColor,
        elevation,
        AppBarDefaults.ContentPadding,
        RectangleShape,
        modifier
    ) {
        if (navigationIcon == null) {
            Spacer(Modifier.width(withoutIconWidth))
        } else {
            Row(
                Modifier.fillMaxHeight().width(iconWidth),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = navigationIcon
                )
            }
        }

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leftSectionWidth != defLeftSectionWidth
                || rightSectionPadding != 0f
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        content = title
                    )
                }
            }
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            with(LocalDensity.current) {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .padding(start = rightSectionPadding.toDp())
                        .onGloballyPositioned {
                            rightSectionWidth = it.size.width.toFloat()
                            if (leftSectionWidth == defLeftSectionWidth
                                && rightSectionWidth != -1f
                                && rightSectionPadding == 0f
                            ) {
                                /*
                                 Find the maximum width of the sections (left or right).
                                 As a result, both sections should have the same width.
                                 */
                                val maxWidth = max(
                                    leftSectionWidth.toPx(),
                                    rightSectionWidth
                                )
                                leftSectionWidth = maxWidth.toDp()
                                rightSectionPadding = abs(rightSectionWidth - maxWidth)
                            }
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}

@Composable
private fun MaxCenterTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
    val defLeftSectionWidth = if (navigationIcon == null) withoutIconWidth else iconWidth
    var leftSectionWidth by remember { mutableStateOf(defLeftSectionWidth) }
    var rightSectionWidth by remember { mutableStateOf(-1f) }
    var rightSectionPadding by remember { mutableStateOf(0f) }

    MaxAppBar(
        backgroundColor,
        contentColor,
        elevation,
        AppBarDefaults.ContentPadding,
        RectangleShape,
        modifier
    ) {
        if (navigationIcon == null) {
            Spacer(Modifier.width(withoutIconWidth))
        } else {
            Row(
                Modifier.fillMaxHeight().width(iconWidth),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = navigationIcon
                )
            }
        }

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leftSectionWidth != defLeftSectionWidth
                || rightSectionPadding != 0f
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        content = title
                    )
                }
            }
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            with(LocalDensity.current) {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .padding(start = rightSectionPadding.toDp())
                        .onGloballyPositioned {
                            rightSectionWidth = it.size.width.toFloat()
                            if (leftSectionWidth == defLeftSectionWidth
                                && rightSectionWidth != -1f
                                && rightSectionPadding == 0f
                            ) {
                                /*
                                 Find the maximum width of the sections (left or right).
                                 As a result, both sections should have the same width.
                                 */
                                val maxWidth = max(
                                    leftSectionWidth.toPx(),
                                    rightSectionWidth
                                )
                                leftSectionWidth = maxWidth.toDp()
                                rightSectionPadding = abs(rightSectionWidth - maxWidth)
                            }
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}

@Composable
private fun MaxAppBar(
    backgroundColor: Color,
    contentColor: Color,
    elevation: Dp,
    contentPadding: PaddingValues,
    shape: Shape,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shadowElevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(AppBarHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTopAppBarCenter(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
        actionIconContentColor = MaterialTheme.colorScheme.onBackground
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        colors = colors,
        title = title,
        actions = actions,
        navigationIcon = navigationIcon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxTopAppBarLeftTitle(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        windowInsets = windowInsets,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        ),
        title = title,
        actions = actions,
        navigationIcon = navigationIcon
    )
}
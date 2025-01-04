package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import kotlinx.coroutines.delay

private val positionalThreshold: (totalDistance: Float) -> Float
    @Composable get() = with(LocalDensity.current) {
        { Dimens.size_100.toPx() }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        },
        positionalThreshold = positionalThreshold
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        AppTheme.colorScheme.error
    } else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(Dimens.size_10))
            .background(color)
            .padding(Dimens.size_16),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.size(Dimens.size_20),
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = AppTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTest() {
    AppTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SwipeToDeleteContainer(
                item = "language",
                onDelete = {

                }
            ) { language ->
                Text(
                    text = language,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colorScheme.background)
                        .padding(Dimens.size_16)
                )
            }
        }
    }
}
package com.jabozaroid.abopay.core.designsystem.component.scrollbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import kotlinx.coroutines.delay

private const val SCROLLBAR_INACTIVE_TO_DORMANT_TIME_IN_MS = 2_000L

/**
 * A [Scrollbar] that allows for fast scrolling of content by dragging its thumb.
 * Its thumb disappears when the scrolling container is dormant.
 * @param modifier a [Modifier] for the [Scrollbar]
 * @param state the driving state for the [Scrollbar]
 * @param orientation the orientation of the scrollbar
 * @param onThumbMoved the fast scroll implementation
 */
@Composable
fun ScrollableState.DraggableScrollbar(
    modifier: Modifier = Modifier,
    state: ScrollbarState,
    orientation: Orientation,
    onThumbMoved: (Float) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Scrollbar(
        modifier = modifier,
        orientation = orientation,
        interactionSource = interactionSource,
        state = state,
        thumb = {
            DraggableScrollbarThumb(
                interactionSource = interactionSource,
                orientation = orientation,
            )
        },
        onThumbMoved = onThumbMoved,
    )
}

/**
 * A simple [Scrollbar].
 * Its thumb disappears when the scrolling container is dormant.
 * @param modifier a [Modifier] for the [Scrollbar]
 * @param state the driving state for the [Scrollbar]
 * @param orientation the orientation of the scrollbar
 */
@Composable
fun ScrollableState.DecorativeScrollbar(
    modifier: Modifier = Modifier,
    state: ScrollbarState,
    orientation: Orientation,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Scrollbar(
        modifier = modifier,
        orientation = orientation,
        interactionSource = interactionSource,
        state = state,
        thumb = {
            DecorativeScrollbarThumb(
                interactionSource = interactionSource,
                orientation = orientation,
            )
        },
    )
}

/**
 * A scrollbar thumb that is intended to also be a touch target for fast scrolling.
 */
@Composable
private fun ScrollableState.DraggableScrollbarThumb(
    interactionSource: InteractionSource,
    orientation: Orientation,
) {
    Box(
        modifier = Modifier
            .run {
                when (orientation) {
                    Orientation.Vertical -> width(Dimens.size_12).fillMaxHeight()
                    Orientation.Horizontal -> height(Dimens.size_12).fillMaxWidth()
                }
            }
            .background(
                color = scrollbarThumbColor(
                    interactionSource = interactionSource,
                ),
                shape = RoundedCornerShape(Dimens.size_16),
            ),
    )
}

/**
 * A decorative scrollbar thumb used solely for communicating a user's position in a list.
 */
@Composable
private fun ScrollableState.DecorativeScrollbarThumb(
    interactionSource: InteractionSource,
    orientation: Orientation,
) {
    Box(
        modifier = Modifier
            .run {
                when (orientation) {
                    Orientation.Vertical -> width(Dimens.size_2).fillMaxHeight()
                    Orientation.Horizontal -> height(Dimens.size_2).fillMaxWidth()
                }
            }
            .background(
                color = scrollbarThumbColor(
                    interactionSource = interactionSource,
                ),
                shape = RoundedCornerShape(Dimens.size_16),
            ),
    )
}

/**
 * The color of the scrollbar thumb as a function of its interaction state.
 * @param interactionSource source of interactions in the scrolling container
 */
@Composable
private fun ScrollableState.scrollbarThumbColor(
    interactionSource: InteractionSource,
): Color {
    var state by remember { mutableStateOf(ThumbState.Dormant) }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val dragged by interactionSource.collectIsDraggedAsState()
    val active = (canScrollForward || canScrollForward) &&
            (pressed || hovered || dragged || isScrollInProgress)

    val color by animateColorAsState(
        targetValue = when (state) {
            ThumbState.Active -> AppTheme.colorScheme.onSurface.copy(0.5f)
            ThumbState.Inactive -> AppTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            ThumbState.Dormant -> Color.Transparent
        },
        animationSpec = SpringSpec(
            stiffness = Spring.StiffnessLow,
        ),
        label = "Scrollbar thumb color",
    )
    LaunchedEffect(active) {
        when (active) {
            true -> state = ThumbState.Active
            false -> if (state == ThumbState.Active) {
                state = ThumbState.Inactive
                delay(SCROLLBAR_INACTIVE_TO_DORMANT_TIME_IN_MS)
                state = ThumbState.Dormant
            }
        }
    }

    return color
}

private enum class ThumbState {
    Active, Inactive, Dormant
}
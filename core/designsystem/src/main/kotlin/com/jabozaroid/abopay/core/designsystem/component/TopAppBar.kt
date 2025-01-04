package com.jabozaroid.abopay.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    @StringRes titleRes: Int? = null,
    title: String? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String = "navigationIconContentDescription",
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String = "actionIconContentDescription",
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = AppTheme.colorScheme.surface,
        actionIconContentColor = AppTheme.colorScheme.onSurface,
        titleContentColor = AppTheme.colorScheme.onBackground,
        navigationIconContentColor = AppTheme.colorScheme.onSurface,
        scrolledContainerColor = AppTheme.colorScheme.onSurface
    ),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {

    CenterAlignedTopAppBar(
        title = {
            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_B) {
                Text(
                    text = if (titleRes != null) aboPayStringResource(id = titleRes) else title ?: ""
                )
            }
        },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = navigationIconContentDescription
                    )
                }
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = actionIconContentDescription
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = Dimens.size_10,
                    bottomEnd = Dimens.size_10
                )
            ),
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    )

}

@Composable
fun AppToolbar(
    modifier: Modifier,
    toolbarTitle: String,
    onLeftIconClicked: (() -> Unit)? = null,
    onRightIconClicked: () -> Unit,
    leftIcon: Int? = null,
    rightIcon: Int? = R.drawable.arrow_circle_left,
) {

    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.size_8,
                vertical = Dimens.size_10
            )
            .clip(RoundedCornerShape(Dimens.size_12)),
        tonalElevation = Dimens.size_4,
        shadowElevation = Dimens.size_4,
        color = AppTheme.colorScheme.background,
        border = BorderStroke(0.25.dp, AppTheme.colorScheme.surface)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.size_10))
        ) {

            val (titleRef, leftIconRef, rightIconRef) = createRefs()

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top, Dimens.size_8)
                    start.linkTo(leftIconRef.end, Dimens.size_8)
                    end.linkTo(rightIconRef.start, Dimens.size_8)
                    bottom.linkTo(parent.bottom, Dimens.size_8)
                    width = Dimension.fillToConstraints
                },
                text = toolbarTitle,
                color = AppTheme.colorScheme.ivaTitleText,
                style = AppTheme.typography.text_13PX_17SP_M.copy(
                    fontWeight = FontWeight.W800
                )
            )


            Image(
                modifier = Modifier
                    .constrainAs(leftIconRef) {
                        start.linkTo(parent.start, Dimens.size_8)
                        top.linkTo(parent.top, Dimens.size_8)
                        bottom.linkTo(parent.bottom, Dimens.size_8)
                        end.linkTo(titleRef.start, Dimens.size_8)
                        width = Dimension.value(Dimens.size_40)
                        height = Dimension.value(Dimens.size_40)
                    }
                    .padding(Dimens.size_5)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onLeftIconClicked?.invoke()
                    }
                    .alpha(if (leftIcon == null) 0f else 1f),
                painter = painterResource(id = leftIcon ?: R.drawable.iva_toolbar_logo),
                contentDescription = "left icon"
            )


            rightIcon?.let {
                Image(
                    modifier = Modifier
                        .constrainAs(rightIconRef) {
                            end.linkTo(parent.end, Dimens.size_8)
                            top.linkTo(parent.top, Dimens.size_8)
                            bottom.linkTo(parent.bottom, Dimens.size_8)
                            start.linkTo(titleRef.end, Dimens.size_8)
                            width = Dimension.value(Dimens.size_40)
                            height = Dimension.value(Dimens.size_40)
                        }
                        .padding(Dimens.size_5)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onRightIconClicked?.invoke()
                        },
                    painter = painterResource(id = it),
                    contentDescription = "right icon"
                )

            } ?: run {
                Box(
                    modifier = Modifier
                        .constrainAs(rightIconRef) {
                            end.linkTo(parent.end, Dimens.size_8)
                            top.linkTo(parent.top, Dimens.size_8)
                            bottom.linkTo(parent.bottom, Dimens.size_8)
                            start.linkTo(titleRef.end, Dimens.size_8)
                            width = Dimension.value(30.dp)
                            height = Dimension.value(30.dp)
                        }
                )
            }

        }
    }

}

//@OptIn(ExperimentalMaterial3Api::class)
//@ThemePreviews
//@Composable
//private fun AppTopAppBarPreview() {
//    AppTheme {
//        TopAppBar(
//            titleRes = R.string.untitled,
//            navigationIcon = AppIcons.Add,
//            navigationIconContentDescription = "Navigation icon",
//            actionIcon = AppIcons.MoreVert,
//            actionIconContentDescription = "Action icon",
//        )
//    }
//}

@ThemePreviews
@Composable
private fun AppToolbarPreview() {
    AppTheme {
        AppToolbar(
            modifier = Modifier.fillMaxWidth(),
            toolbarTitle = "ابو پی",
            onRightIconClicked = {}
        )
    }
}


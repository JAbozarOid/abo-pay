package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Dark_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Light_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_50

@Composable
fun AppFab(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        FloatingActionButton(
            onClick = { onClick.invoke() },
            containerColor = Blue_Dark_80,
            contentColor = Gray_Light_50,
            content = content
        )
    }

}

@Composable
fun AppMultiItemFab(
    expanded: Boolean = false,
    containerColor: Color = Blue_Dark_80,
    contentColor: Color = Blue_Light_80,
    content: @Composable ColumnScope.() -> Unit,
) {

    var isExpanded by remember { mutableStateOf(expanded) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = isExpanded
        ) {
            Column(content = content)
        }
        FloatingActionButton(
            onClick = {
                isExpanded = !isExpanded
            },
            containerColor = containerColor,
            contentColor = contentColor,
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = AppIcons.Line,
                    contentDescription = "expand"
                )
            }
            AnimatedVisibility(
                visible = !isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(imageVector = AppIcons.Add, contentDescription = "expand")
            }

        }
    }

}

@Composable
fun AppFabItem(
    modifier: Modifier = Modifier,
    containerColor: Color = Blue_Dark_80,
    contentColor: Color = Blue_Light_80,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {

    AppButton(
        modifier = modifier
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        ProvideTextStyle(
            value = AppTheme.typography.text_9PX_12SP_M
        ) {
            Text(
                text = title,
                color = contentColor
            )
        }
        Spacer(modifier = Modifier.width(Dimens.size_10))
        Icon(
            modifier = Modifier.size(
                width = Dimens.size_40,
                height = Dimens.size_40
            ),
            imageVector = icon,
            contentDescription = "icon $title",
            tint = contentColor
        )
    }

}


@Composable
@ThemePreviews
fun PreviewAppFab() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AppFab(onClick = {}) {
                Icon(imageVector = AppIcons.Add, contentDescription = "add")
            }
            Spacer(modifier = Modifier.height(Dimens.size_10))
            AppMultiItemFab(
                expanded = true,
                containerColor = Blue_Dark_80,
                contentColor = Gray_Light_50
            ) {
                AppFabItem(
                    containerColor = Blue_Dark_80,
                    contentColor = Gray_Light_50,
                    modifier = Modifier
                        .padding(bottom = Dimens.size_10, start = Dimens.size_10),
                    icon = AppIcons.Add,
                    title = "Add Category",
                    onClick = {}
                )
                AppFabItem(
                    containerColor = Blue_Dark_80,
                    contentColor = Gray_Light_50,
                    modifier = Modifier
                        .padding(bottom = Dimens.size_10, start = Dimens.size_10),
                    icon = AppIcons.Settings,
                    title = "Add Task",
                    onClick = {}
                )
            }
        }
    }
}
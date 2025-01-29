package com.jabozaroid.abopay.core.designsystem.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.AmountUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit,
    onBtn1Click: () -> Unit = {},
    onBtn2Click: () -> Unit = {},
    showDefaultButtons: Boolean = true,
    btn1Text: String = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.confirm),
    btn2Text: String = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.cancel),
    isBtn1Enabled: Boolean = true,
    dualActionBtn: Boolean = true,
    isScrollable: Boolean = false,
    hasMaxHeight: Boolean = false,
) {

    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val bottomSheetMaxHeight = screenHeight * 0.4f

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { },
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.background,
        modifier = Modifier
            .offset(y = if (hasMaxHeight) bottomSheetMaxHeight.dp else 0.dp),
        properties = ModalBottomSheetProperties(
            securePolicy = SecureFlagPolicy.SecureOff,
            shouldDismissOnBackPress = true
        ),
        content = {
            BottomSheet(
                modifier = Modifier
                    .fillMaxWidth(),
                title = title,
                content = content,
                btn2Text = btn2Text,
                btn1Text = btn1Text,
                showDefaultButtons = showDefaultButtons,
                onBtn1Click = onBtn1Click, onBtn2Click = onBtn2Click,
                isBtn1Enabled = isBtn1Enabled,
                dualActionBtn = dualActionBtn,
                isScrollable = isScrollable,
                onDismiss = onDismiss
            )
        }
    )
}

@Composable
private fun BottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
    onBtn1Click: () -> Unit = {},
    onBtn2Click: () -> Unit = {},
    showDefaultButtons: Boolean = true,
    btn1Text: String = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.confirm),
    btn2Text: String = aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.cancel),
    isBtn1Enabled: Boolean = true,
    dualActionBtn: Boolean = true,
    isScrollable: Boolean = false,
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(AppTheme.colorScheme.background)
    ) {

        // top row with title and dismiss icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.size_24, bottom = Dimens.size_10)
        ) {

            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(Dimens.size_16)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onDismiss()
                    },
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "left icon"
            )

            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Center),
                color = AppTheme.colorScheme.aboTitleText,
                style = AppTheme.typography.text_13PX_17SP_M.copy(
                    fontWeight = FontWeight.W800
                )
            )

        }

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = Dimens.size_24)
                .background(AppTheme.colorScheme.primary)
                .height(Dimens.size_2)
        )
        Spacer(modifier = Modifier.height(Dimens.size_16))

        // content
        Column(
            modifier = if (isScrollable)
                modifier
                    .background(AppTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            else
                modifier.background(AppTheme.colorScheme.background)
        ) {
            content()
        }

        // at the bottom of the bottom sheet there are two buttons
        if (showDefaultButtons) {
            ButtonsBox(
                onBtn1Click = onBtn1Click, onBtn2Click = onBtn2Click,
                button1Title = btn1Text,
                button2Title = btn2Text,
                isBtn1Enabled = isBtn1Enabled,
                dualActionBtn = dualActionBtn
            )
        }
    }
}


@Composable
fun ButtonsBox(
    button1Title: String = "",
    button2Title: String = "",
    onBtn1Click: () -> Unit,
    onBtn2Click: () -> Unit,
    isBtn1Enabled: Boolean = true,
    dualActionBtn: Boolean = true,
) {
    Row(
        modifier = Modifier
            .padding(
                vertical = Dimens.size_24,
                horizontal = Dimens.size_8
            )
            .background(AppTheme.colorScheme.background)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (dualActionBtn)
        //Button2
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = Dimens.size_10)

            ) {
                AppOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp),
                    onClick = onBtn2Click,
                    text = {
                        ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                            Text(
                                text = button2Title,
                                maxLines = 1,
                            )
                        }
                    }
                )
            }
        Spacer(modifier = Modifier.width(Dimens.size_4))
        //Button1
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(end = Dimens.size_10, start = Dimens.size_4)
        ) {
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp),
                onClick = onBtn1Click,
                enabled = isBtn1Enabled,
                text = {
                    ProvideTextStyle(value = AppTheme.typography.text_12PX_16SP_M) {
                        Text(
                            text = button1Title,
                            maxLines = 1,
                        )
                    }

                })
        }
    }
}


@ThemePreviews
@Composable
fun PreviewBottomSheetComponent() {
    AppTheme {
        BottomSheet(
            title = "انتخاب شارژ",
            content = {
                SwitchComponent(
                    titles = listOf("عادی", "شگفت انگیز"),
                    onTitleSelected = {},
                    modifier = Modifier.padding(
                        horizontal = Dimens.size_18,
                        vertical = Dimens.size_5
                    )
                )
                AmountGridComponent(
                    modifier = Modifier.padding(
                        horizontal = Dimens.size_18,
                    ),
                    items = listOf(
                        AmountUiModel(amount = "50.000"),
                        AmountUiModel(amount = "100.000"),
                        AmountUiModel(amount = "200.000"),
                        AmountUiModel(amount = "20.00"),
                        AmountUiModel(amount = "300.000"),
                        AmountUiModel(amount = "5.000"),
                        AmountUiModel(amount = "10.0"),
                    ), onItemClick = {})
            },
            onBtn1Click = {},
            onBtn2Click = {},

            )
    }

}

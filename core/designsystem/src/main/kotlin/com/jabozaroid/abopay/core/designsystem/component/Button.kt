package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 * Mark filled button with generic content slot. Wraps Material 3 [Button].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(Dimens.size_0),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colorScheme.primary,
        contentColor = AppTheme.colorScheme.aboButtonText1
    ),
    isLoading: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = (!isLoading && enabled),
        colors = colors,
        shape = RoundedCornerShape(Dimens.size_8),
        content = {
            if (isLoading)
                ButtonLoading()
            else this.content()
        },
        contentPadding = contentPadding

    )
}

@Composable
fun ButtonLoading() {
    AppLoadingWheel("app loading")
}

@Composable
fun AppPrimaryButton(
    onClick: () -> Unit,
    modifier : Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    text: String
) {
    AppButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        isLoading = isLoading,
    ) {
        ProvideTextStyle(value = AppTheme.typography.text_11PX_15SP_B) {
            Text(text)
        }
    }
}


/**
 * Mark filled button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.height(48.dp),
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {

    AppButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,

        contentPadding = if (leadingIcon != null) ButtonDefaults.ButtonWithIconContentPadding else ButtonDefaults.ContentPadding
    ) {
        AppButtonContent(text = text, leadingIcon = leadingIcon)
    }

}

/**
 * Mark outlined button with generic content slot. Wraps Material 3 [OutlinedButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = AppTheme.colorScheme.aboOutlineButtonText
    ),
    borderColor: Color = AppTheme.colorScheme.outline,
    content: @Composable RowScope.() -> Unit,

    ) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        colors = colors,
        border = BorderStroke(
            width = AppButtonDefaults.OutlineButtonBorderWidth,
            color = if (enabled) borderColor else AppTheme.colorScheme.outline.copy(
            )
        ),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(Dimens.size_8),
        content = content
    )
}


/**
 * Mark outlined button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    borderColor: Color= AppTheme.colorScheme.outline,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppOutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        contentPadding = contentPadding,
        borderColor = borderColor
    ) {
        AppButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}


/**
 * Mark text button with generic content slot. Wraps Material 3 [TextButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param content The button content.
 */
@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = AppTheme.colorScheme.onBackground
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors,
        contentPadding = contentPadding,
        content = content,
    )
}


/**
 * Mark text button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        AppButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}


/**
 * Internal Mark button content layout for arranging the text label and leading icon.
 *
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Default is `null` for no leading icon.Ï
 */
@Composable
fun AppButtonContent(text: @Composable () -> Unit, leadingIcon: @Composable() (() -> Unit)?) {

    Box(
        modifier = Modifier.padding(
            end = if (leadingIcon != null) ButtonDefaults.IconSpacing else Dimens.size_0,
        )
    ) {
        text()
    }
    if (leadingIcon != null) {
        Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    tint: Color = AppTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    AppButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.wrapContentWidth(),
            painter = painterResource(id = icon),
            contentDescription = "icon",
            tint = tint
        )
    }
}


@ThemePreviews
@Composable
fun AppButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun AppOutlinedButtonPreview() {
    AppTheme() {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppOutlinedButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun AppButtonPreview2() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun AppButtonLeadingIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = AppIcons.Add, contentDescription = null) },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppTextButtonLeadingIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppTextButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = AppIcons.Add, contentDescription = null) },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppTextButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppTextButton(
                onClick = {},
                text = { Text("Test button") }
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppIconButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppIconButton(
                onClick = {},
                icon = R.drawable.ic_eye
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppPrimaryButtonPreview() {
    AppTheme() {
        AppPrimaryButton(
            onClick = {},
            text = "ورود"
        )
    }
}

@Preview
@Composable
fun AppLoadingButton() {
    AppTheme {
        AppPrimaryButton(
            onClick = {},
            text = "ورود",
            isLoading = true
        )
    }
}

object AppButtonDefaults {

    const val DisabledOutlineButtonBorderAlpha = 0.12f

    val OutlineButtonBorderWidth = Dimens.size_1

}
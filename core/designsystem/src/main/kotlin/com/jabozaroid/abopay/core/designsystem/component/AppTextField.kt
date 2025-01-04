package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jabozaroid.abopay.core.designsystem.icon.AppIcons
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens.size_8
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_10


@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readonly: Boolean = false,
    textStyle: TextStyle =
        TextStyle(
            fontFamily = AppTheme.typography.text_10PX_13SP_M.fontFamily,
            fontWeight = FontWeight.W600,
            letterSpacing = 0.15.em
        ),
    label: String? = null,
    placeHolder: String? = null,
    placeHolderAlignment: Alignment = Alignment.Center,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = RoundedCornerShape(Dimens.size_8),
    textAlign: TextAlign = TextAlign.Center,
    textDirection: TextDirection = TextDirection.Rtl,
    cursorColor: Color = AppTheme.colorScheme.ivaTextFieldHint,
    labelStyle: TextStyle = AppTheme.typography.text_12PX_16SP_B.copy(fontWeight = FontWeight.W800),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        disabledLabelColor = AppTheme.colorScheme.ivaBackgroundOutlineButton,
        disabledContainerColor = AppTheme.colorScheme.ivaDisableButtonBackground,
        disabledLeadingIconColor = AppTheme.colorScheme.ivaBackgroundOutlineButton,
        disabledPlaceholderColor = AppTheme.colorScheme.ivaBackgroundOutlineButton,
        disabledSupportingTextColor = AppTheme.colorScheme.ivaDisableButtonBackground,
        disabledTextColor = AppTheme.colorScheme.ivaBackgroundOutlineButton,
        disabledTrailingIconColor = AppTheme.colorScheme.ivaDisableButtonBackground,
        disabledBorderColor = AppTheme.colorScheme.ivaDisableButtonBackground,

        errorTextColor = AppTheme.colorScheme.error,
        errorCursorColor = AppTheme.colorScheme.error,
        errorContainerColor = Color.Transparent,
        errorBorderColor = AppTheme.colorScheme.error,
        errorLabelColor = AppTheme.colorScheme.error,
        errorLeadingIconColor = AppTheme.colorScheme.error,
        errorPlaceholderColor = Gray_Dark_10,
        errorSupportingTextColor = AppTheme.colorScheme.error,
        errorTrailingIconColor = AppTheme.colorScheme.error,

        unfocusedLabelColor = AppTheme.colorScheme.onSurface,
        unfocusedBorderColor = AppTheme.colorScheme.ivaDisableTextFieldOutline,
        unfocusedContainerColor = AppTheme.colorScheme.ivaTextFieldBackground,
        unfocusedLeadingIconColor = AppTheme.colorScheme.ivaDetailDots,
        unfocusedPlaceholderColor = AppTheme.colorScheme.ivaDisableTextFieldOutline,
        unfocusedTextColor = AppTheme.colorScheme.ivaOutlineButtonText,
        unfocusedSupportingTextColor = AppTheme.colorScheme.outline,
        unfocusedTrailingIconColor = AppTheme.colorScheme.ivaDetailDots,

        focusedContainerColor = AppTheme.colorScheme.ivaTextFieldBackground,
        focusedBorderColor = AppTheme.colorScheme.ivaDisableTextFieldOutline,
        focusedLabelColor = AppTheme.colorScheme.primary,
        focusedLeadingIconColor = AppTheme.colorScheme.ivaDetailDots,
        focusedPlaceholderColor = AppTheme.colorScheme.ivaDisableTextFieldOutline,
        focusedTextColor = AppTheme.colorScheme.ivaOutlineButtonText,
        focusedSupportingTextColor = AppTheme.colorScheme.onSurface,
        focusedTrailingIconColor = AppTheme.colorScheme.ivaDetailDots,

        cursorColor = cursorColor,

        ),
) {

    Column(
        modifier = modifier.padding(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        label?.let {
            AppTextFieldLabel(
                value = label,
                labelStyle = labelStyle,
                enable = enabled,
                disabledTextColor = colors.disabledTextColor,
                modifier = Modifier.padding(horizontal = Dimens.size_5)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.size_0))

        Box {

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readonly,
                textStyle = textStyle.copy(
                    textAlign = textAlign,
                    textDirection = textDirection
                ),
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = null,
                suffix = null,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                minLines = 1,
                interactionSource = remember { MutableInteractionSource() },
                shape = shape,
                colors = colors,
            )

            placeHolder?.let {
                if (value.isEmpty() || value.isBlank()) {
                    AppTextFieldPlaceHolder(
                        value = placeHolder,
                        Modifier
                            .align(alignment = placeHolderAlignment)
                            .matchParentSize()
                            .padding(horizontal = Dimens.size_10)
                    )
                }
            }

        }

        if (!supportingText.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(Dimens.size_4))
            AppTextFieldSupportingText(
                value = supportingText,
                modifier = Modifier.padding(horizontal = Dimens.size_5),
                color = if (isError) AppTheme.colorScheme.error else AppTheme.colorScheme.onSurface
            )
        }

    }
}

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readonly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 10.sp,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        disabledLabelColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        disabledLeadingIconColor = Color.Transparent,
        disabledPlaceholderColor = Color.Transparent,
        disabledSupportingTextColor = Color.Transparent,
        disabledTextColor = AppTheme.colorScheme.onSurface,
        disabledTrailingIconColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,

        unfocusedBorderColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        unfocusedTextColor = AppTheme.colorScheme.onBackground,
        unfocusedSupportingTextColor = AppTheme.colorScheme.outline,
        unfocusedTrailingIconColor = AppTheme.colorScheme.onSurface,

        focusedContainerColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        focusedTextColor = AppTheme.colorScheme.onBackground,

        cursorColor = AppTheme.colorScheme.primary
    ),
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readonly,

        textStyle = textStyle.copy(
            textDirection = TextDirection.Rtl,
            fontFamily = AppTheme.typography.text_9PX_12SP_B.fontFamily,
            textAlign = textAlign,
            fontSize = fontSize,
            fontWeight = FontWeight.W500
        ),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        minLines = 1,
        interactionSource = remember { MutableInteractionSource() },
        colors = colors
    )

}

@Composable
fun AppTextNavItem(value: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.basicMarquee(),
        style = AppTheme.typography.text_9PX_12SP_M,
        text = value,
        maxLines = 1,
    )
}

@Composable
fun AppTextFieldLabel(
    value: String,
    modifier: Modifier = Modifier,
    labelStyle: TextStyle = AppTheme.typography.text_12PX_16SP_B.copy(
        fontWeight = FontWeight.W800
    ),
    disabledTextColor: Color = AppTheme.colorScheme.ivaTitleText,
    enable: Boolean = true,
) {
    ProvideTextStyle(value = labelStyle) {
        Text(
            textAlign = TextAlign.Center,
            color = if (enable) AppTheme.colorScheme.ivaTitleText else disabledTextColor,
            modifier = modifier,
            style = labelStyle,
            text = value
        )
    }
}

@Composable
fun AppTextFieldLabelWithIcon(
    value: String,
    modifier: Modifier = Modifier,
    icon: Int,
    subtitle: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != -1)
            Image(
                modifier = Modifier.padding(end = size_8),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        Text(
            style = AppTheme.typography.text_11PX_15SP_B.copy(
                color = AppTheme.colorScheme.ivaTitleText2
            ),
            text = value
        )
    }
}

@Composable
fun AppTextFieldPlaceHolder(value: String, modifier: Modifier = Modifier) {
    ProvideTextStyle(
        value = AppTheme.typography.text_11PX_15SP_B.copy(
            textDirection = TextDirection.Rtl,

            )
    ) {
        Text(
            color = AppTheme.colorScheme.ivaTextFieldHint,
            modifier = modifier,
            textAlign = TextAlign.Center,
            text = value
        )
    }
}

@Composable
fun OTPInput(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    size: Int,
) {


    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length <= size) onValueChange.invoke(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(size) { index ->
                    OTPCharView(
                        index = index,
                        text = value
                    )
                    Spacer(modifier = Modifier.width(Dimens.size_8))
                }
            }
        }
    )

}

@Composable
private fun OTPCharView(
    index: Int,
    text: String,
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_B) {
        Text(
            maxLines = 1,
            modifier = Modifier
                .width(Dimens.size_40)
                .height(50.dp)
                .border(
                    Dimens.size_1, when {
                        isFocused -> AppTheme.colorScheme.primary
                        else -> AppTheme.colorScheme.ivaDisableTextFieldHint
                    }, RoundedCornerShape(Dimens.size_8)
                )
                .padding(Dimens.size_2)
                .background(AppTheme.colorScheme.ivaTextFieldBackground),
            text = char,
            color = if (isFocused) {
                AppTheme.colorScheme.primary
            } else {
                AppTheme.colorScheme.onSurface
            },
            textAlign = TextAlign.Center,
            lineHeight = 50.sp
        )
    }

}


@Composable
fun AppTextFieldItemsLabel(value: String, modifier: Modifier = Modifier) {
    Text(
        color = AppTheme.colorScheme.ivaTitleText,
        modifier = modifier,
        style = AppTheme.typography.text_8PX_10SP_B.copy(
            textAlign = TextAlign.Center
        ),
        text = value
    )
}

@Composable
fun AppTextFieldSupportingText(
    value: String,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colorScheme.onSurface,
) {
    ProvideTextStyle(
        value = AppTheme.typography.text_9PX_12SP_B
    ) {
        Text(
            color = color,
            modifier = modifier,
            textAlign = TextAlign.Center,
            text = value
        )
    }
}

@ThemePreviews
@Composable
private fun PreviewOTPInput() {

    AppTheme {

        var value by remember {
            mutableStateOf("")
        }


        OTPInput(
            modifier = Modifier.fillMaxWidth(),
            value = value, onValueChange = { value = it },
            size = 6
        )

    }

}

@Composable
@ThemePreviews
fun PreviewAppTextField() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(Dimens.size_10)
        ) {

            var text by rememberSaveable { mutableStateOf("") }

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = "Hello i am inner label",
                placeHolder = "I Am PlaceHolder",
                leadingIcon = {
                    Icon(imageVector = AppIcons.Add, contentDescription = "leading icon")
                },
                trailingIcon = {

                },
                supportingText = "supporting text"
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = "شماره تلفن",
                placeHolder = "نمونه",
                leadingIcon = {
                    Icon(imageVector = AppIcons.Add, contentDescription = "leading icon")
                },
                supportingText = "توضیحات بیشتر"
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = "disable label",
                enabled = false,

                placeHolder = "disable place holder",
                leadingIcon = {
                    Icon(imageVector = AppIcons.Add, contentDescription = "leading icon")
                },
                trailingIcon = {
                    Icon(
                        imageVector = AppIcons.BookmarkBorder,
                        contentDescription = "trailing icon"
                    )
                },
                supportingText = "supporting text"
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = "error label",
                isError = true,
                placeHolder = "I am error",
                leadingIcon = {
                    Icon(imageVector = AppIcons.Add, contentDescription = "leading icon")
                },
                trailingIcon = {
                    Icon(
                        imageVector = AppIcons.BookmarkBorder,
                        contentDescription = "trailing icon"
                    )
                },
                supportingText = "supporting text"
            )

            SimpleTextField(
                value = "6037 9972 6372 8496",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                onValueChange = { text = it },
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            AppTextField(
                modifier = Modifier
                    .weight(0.6f),
                value = text,
                onValueChange = { text = it },
                label = "error label",
                isError = false,
                placeHolder = "I am error",
                supportingText = "supporting text"
            )
        }
    }
}


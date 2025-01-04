package com.jabozaroid.abopay.core.designsystem.theme.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorScheme(
    val background: Color,
    val background3: Color,
    val background4: Color,
    val bottomNavBackground: Color,
    val background2: Color,
    val onBackground: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val outline: Color,
    val error: Color,
    val onSurface: Color,
    val surface: Color,
    val success: Color,
    val cardBackground: Color,

    /**Add More color*/
    val ivaTitleText: Color,
    val ivaTitleText2: Color,
    val ivaTextFieldHint: Color,
    val ivaTitleText3: Color,
    val ivaDescriptionText: Color,
    val ivaButtonText1: Color,
    val ivaOutlineButtonText: Color,
    val ivaIntroTitleText: Color,
    val ivaTextFieldBackground: Color,
    val ivaBackgroundOutlineButton: Color,
    val ivaBackgroundButton1: Color,
    val ivaBackgroundButton2: Color,
    val ivaBackgroundButton3: Color,
    val ivaDisableButtonBackground: Color,


    val ivaLine: Color,
    val disable: Color,

    val ivaDetailText: Color,
    val ivaDetailDots: Color,
    val ivaButtonSheetLine: Color,
    val ivaDisableTextFieldOutline: Color,
    val ivaDisableTextFieldLabel: Color,
    val ivaDisableTextFieldHint: Color,
    val ivaIconToolbar: Color,
    val ivaDownloadButtonBackground: Color,

    val bottomNavItem: Color,

    val ivaBackgroundScreen: Color,
    val ivaWhiteBackground: Color,
    val ivaLightGrayBackground: Color,
    val ivaTimerBackground: Color,

    val ivaSwitchSelected: Color,
    val ivaSwitchUnselected: Color,
    val ivaCardBackground: Color,
    val ivaNoticeTextColor: Color,

    val ivaGraySwitchSelected: Color,
    val ivaGraySwitchUnselected: Color,

    val ivaBalanceInformationBackground: Color,

    val kahrobaDivider: Color,
    val kahrobaHelperCircle: Color,

    )

/**
 * A composition local for [AppColorScheme]
 */
val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        background = Color.Unspecified,
        background3 = Color.Unspecified,
        background4 = Color.Unspecified,
        bottomNavBackground = Color.Unspecified,
        background2 = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        outline = Color.Unspecified,
        error = Color.Unspecified,
        onSurface = Color.Unspecified,
        surface = Color.Unspecified,
        success = Color.Unspecified,


        ivaTitleText = Color.Unspecified,
        ivaTitleText2 = Color.Unspecified,
        ivaTextFieldHint = Color.Unspecified,
        ivaButtonText1 = Color.Unspecified,
        ivaDisableButtonBackground = Color.Unspecified,
        ivaOutlineButtonText = Color.Unspecified,
        ivaBackgroundOutlineButton = Color.Unspecified,
        ivaDetailText = Color.Unspecified,
        ivaDetailDots = Color.Unspecified,
        ivaButtonSheetLine = Color.Unspecified,
        ivaDisableTextFieldOutline = Color.Unspecified,
        ivaDisableTextFieldLabel = Color.Unspecified,
        ivaDisableTextFieldHint = Color.Unspecified,
        ivaIconToolbar = Color.Unspecified,
        ivaDownloadButtonBackground = Color.Unspecified,
        ivaDescriptionText = Color.Unspecified,
        ivaIntroTitleText = Color.Unspecified,
        ivaTextFieldBackground = Color.Unspecified,
        ivaBackgroundButton1 = Color.Unspecified,
        ivaBackgroundButton2 = Color.Unspecified,
        ivaBackgroundButton3 = Color.Unspecified,

        bottomNavItem = Color.Unspecified,
        ivaLine = Color.Unspecified,
        disable = Color.Unspecified,

        ivaBackgroundScreen = Color.Unspecified,
        ivaWhiteBackground = Color.Unspecified,
        ivaLightGrayBackground = Color.Unspecified,
        ivaTimerBackground = Color.Unspecified,
        ivaSwitchSelected = Color.Unspecified,
        ivaSwitchUnselected = Color.Unspecified,
        ivaCardBackground = Color.Unspecified,
        ivaNoticeTextColor = Color.Unspecified,
        ivaTitleText3 = Color.Unspecified,
        cardBackground = Color.Unspecified,

        ivaGraySwitchSelected = Color.Unspecified,
        ivaGraySwitchUnselected = Color.Unspecified,
        ivaBalanceInformationBackground = Color.Unspecified,
        kahrobaDivider = Color.Unspecified,
        kahrobaHelperCircle = Color.Unspecified
    )
}

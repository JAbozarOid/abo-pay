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
    val aboTitleText: Color,
    val aboTitleText2: Color,
    val aboTextFieldHint: Color,
    val aboTitleText3: Color,
    val aboDescriptionText: Color,
    val aboButtonText1: Color,
    val aboOutlineButtonText: Color,
    val aboIntroTitleText: Color,
    val aboTextFieldBackground: Color,
    val aboBackgroundOutlineButton: Color,
    val aboBackgroundButton1: Color,
    val aboBackgroundButton2: Color,
    val aboBackgroundButton3: Color,
    val aboDisableButtonBackground: Color,


    val aboLine: Color,
    val disable: Color,

    val aboDetailText: Color,
    val aboDetailDots: Color,
    val aboButtonSheetLine: Color,
    val aboDisableTextFieldOutline: Color,
    val aboDisableTextFieldLabel: Color,
    val aboDisableTextFieldHint: Color,
    val aboIconToolbar: Color,
    val aboDownloadButtonBackground: Color,

    val bottomNavItem: Color,

    val aboBackgroundScreen: Color,
    val aboWhiteBackground: Color,
    val aboLightGrayBackground: Color,
    val aboTimerBackground: Color,

    val aboSwitchSelected: Color,
    val aboSwitchUnselected: Color,
    val aboCardBackground: Color,
    val aboNoticeTextColor: Color,

    val aboGraySwitchSelected: Color,
    val aboGraySwitchUnselected: Color,

    val aboBalanceInformationBackground: Color,

    val kahrobaDivider: Color,
    val kahrobaHelperCircle: Color,

    val messengerBackground: Color

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


        aboTitleText = Color.Unspecified,
        aboTitleText2 = Color.Unspecified,
        aboTextFieldHint = Color.Unspecified,
        aboButtonText1 = Color.Unspecified,
        aboDisableButtonBackground = Color.Unspecified,
        aboOutlineButtonText = Color.Unspecified,
        aboBackgroundOutlineButton = Color.Unspecified,
        aboDetailText = Color.Unspecified,
        aboDetailDots = Color.Unspecified,
        aboButtonSheetLine = Color.Unspecified,
        aboDisableTextFieldOutline = Color.Unspecified,
        aboDisableTextFieldLabel = Color.Unspecified,
        aboDisableTextFieldHint = Color.Unspecified,
        aboIconToolbar = Color.Unspecified,
        aboDownloadButtonBackground = Color.Unspecified,
        aboDescriptionText = Color.Unspecified,
        aboIntroTitleText = Color.Unspecified,
        aboTextFieldBackground = Color.Unspecified,
        aboBackgroundButton1 = Color.Unspecified,
        aboBackgroundButton2 = Color.Unspecified,
        aboBackgroundButton3 = Color.Unspecified,

        bottomNavItem = Color.Unspecified,
        aboLine = Color.Unspecified,
        disable = Color.Unspecified,

        aboBackgroundScreen = Color.Unspecified,
        aboWhiteBackground = Color.Unspecified,
        aboLightGrayBackground = Color.Unspecified,
        aboTimerBackground = Color.Unspecified,
        aboSwitchSelected = Color.Unspecified,
        aboSwitchUnselected = Color.Unspecified,
        aboCardBackground = Color.Unspecified,
        aboNoticeTextColor = Color.Unspecified,
        aboTitleText3 = Color.Unspecified,
        cardBackground = Color.Unspecified,

        aboGraySwitchSelected = Color.Unspecified,
        aboGraySwitchUnselected = Color.Unspecified,
        aboBalanceInformationBackground = Color.Unspecified,
        kahrobaDivider = Color.Unspecified,
        kahrobaHelperCircle = Color.Unspecified,
        messengerBackground = Color.Unspecified
    )
}

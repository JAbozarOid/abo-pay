package com.jabozaroid.abopay.core.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.AppBackground
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.AppColorScheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.AppShape
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.AppTypography
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Dark_50
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Dark_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Light_100
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Light_60
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.DANA
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_01
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_10
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_100
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_16
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_18
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_20
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_40
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_60
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_70
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Dark_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_100
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_20
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_50
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Gray_Light_90
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Green_100
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.LocalAppBackground
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.LocalAppColorScheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.LocalAppShape
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.LocalAppTypographyScheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Messenger_Green_Dark
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Messenger_Green_Light
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_05
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_10
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_100
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_20
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_40
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Orange_80
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Red_100


private val darkColorScheme = AppColorScheme(


    //region TextColors
    aboTitleText = Gray_Dark_100,
    aboTitleText2 = Blue_Dark_50,
    aboTextFieldHint = Gray_Dark_20,
    aboDescriptionText = Gray_Dark_100,
    aboButtonText1 = Gray_Dark_100,
    aboIntroTitleText = Blue_Dark_80,
    aboOutlineButtonText = Gray_Dark_80,
    aboTextFieldBackground = Gray_Light_20,
    //endregion

    //region Background of button colors
    aboBackgroundOutlineButton = Gray_Dark_40,
    aboBackgroundButton1 = Orange_100,
    aboBackgroundButton2 = Gray_Light_50,
    aboBackgroundButton3 = Blue_Light_60,
    aboDisableButtonBackground = Gray_Dark_10,
    //endregion
    primary = Orange_80,

    //region ActionColors
    error = Red_100,
    success = Green_100,
    //endregion

    background = Gray_Light_100,
    background2 = Gray_Light_90,
    background3 = Blue_Light_100,
    background4 = Gray_Light_80,
    bottomNavBackground = Blue_Dark_50,

    onBackground = Gray_Dark_100,
    onPrimary = Orange_80,
    secondary = Gray_Dark_100,
    onSecondary = Orange_80,
    outline = Gray_Dark_100,
    surface = Color.Transparent,
    onSurface = Gray_Dark_100,
    aboDetailText = Gray_Dark_80,
    aboDetailDots = Gray_Dark_70,
    aboButtonSheetLine = Orange_80,
    aboDisableTextFieldOutline = Gray_Dark_20,
    aboDisableTextFieldLabel = Gray_Dark_100,
    aboDisableTextFieldHint = Gray_Dark_100,
    aboIconToolbar = Blue_Dark_50,
    aboDownloadButtonBackground = Orange_80,
    aboLine = Gray_Dark_10,
    disable = Gray_Dark_60,
    aboBackgroundScreen = Gray_Light_90,
    aboWhiteBackground = Gray_Light_100,
    aboLightGrayBackground = Gray_Dark_70,

    bottomNavItem = Orange_20,
    aboTimerBackground = Blue_Light_60,
    aboSwitchSelected = Orange_40,
    aboSwitchUnselected = Orange_10,
    aboCardBackground = Gray_Dark_10,
    aboNoticeTextColor = Gray_Dark_40,
    aboTitleText3 = Gray_Dark_70,
    cardBackground = Gray_Dark_16,

    aboGraySwitchSelected = Gray_Dark_10,
    aboGraySwitchUnselected = Gray_Light_20,

    aboBalanceInformationBackground = Orange_05,
    kahrobaDivider = Gray_Dark_18,
    kahrobaHelperCircle = Gray_Dark_01,
    messengerDarkBackground = Messenger_Green_Dark,
    messengerLightBackground = Messenger_Green_Light

)

private val lightColorScheme = AppColorScheme(


    //region TextColors
    aboTitleText = Gray_Dark_100,
    aboTitleText2 = Blue_Dark_50,
    aboTextFieldHint = Gray_Dark_20,
    aboDescriptionText = Gray_Dark_100,
    aboButtonText1 = Gray_Dark_100,
    aboIntroTitleText = Blue_Dark_80,
    aboOutlineButtonText = Gray_Dark_80,
    aboTextFieldBackground = Gray_Light_20,
    //endregion

    //region Background of button colors
    aboBackgroundOutlineButton = Gray_Dark_40,
    aboBackgroundButton1 = Orange_100,
    aboBackgroundButton2 = Gray_Light_50,
    aboBackgroundButton3 = Blue_Light_60,
    aboDisableButtonBackground = Gray_Dark_10,
    //endregion
    primary = Orange_80,

    //region ActionColors
    error = Red_100,
    success = Green_100,
    //endregion

    background = Gray_Light_100,
    background3 = Blue_Light_100,
    background4 = Gray_Light_80,
    bottomNavBackground = Blue_Dark_50,
    background2 = Gray_Light_90,

    onBackground = Gray_Dark_100,
    onPrimary = Orange_80,
    secondary = Gray_Dark_100,
    onSecondary = Orange_80,
    outline = Gray_Dark_100,
    surface = Color.Transparent,
    onSurface = Gray_Dark_100,
    aboDetailText = Gray_Dark_80,
    aboDetailDots = Gray_Dark_70,
    aboButtonSheetLine = Orange_80,
    aboDisableTextFieldOutline = Gray_Dark_10,
    aboDisableTextFieldLabel = Gray_Dark_100,
    aboDisableTextFieldHint = Gray_Dark_10,
    aboIconToolbar = Blue_Dark_50,
    aboDownloadButtonBackground = Orange_80,
    aboLine = Gray_Dark_10,
    disable = Gray_Dark_60,

    aboBackgroundScreen = Gray_Light_90,
    aboWhiteBackground = Gray_Light_100,
    aboLightGrayBackground = Gray_Dark_70,

    bottomNavItem = Orange_20,

    aboTimerBackground = Blue_Light_60,

    aboSwitchSelected = Orange_40,
    aboSwitchUnselected = Orange_10,
    aboCardBackground = Gray_Dark_10,
    aboNoticeTextColor = Gray_Dark_40,
    aboTitleText3 = Gray_Dark_70,
    cardBackground = Gray_Dark_16,

    aboGraySwitchSelected = Gray_Dark_10,
    aboGraySwitchUnselected = Gray_Light_20,
    aboBalanceInformationBackground = Orange_05,
    kahrobaDivider = Gray_Dark_18,
    kahrobaHelperCircle = Gray_Dark_01,
    messengerDarkBackground = Messenger_Green_Dark,
    messengerLightBackground = Messenger_Green_Light

)

private val typography = AppTypography(
    text_8PX_10SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ).copy(textDirection = TextDirection.Rtl),

    text_8PX_10SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    ).copy(textDirection = TextDirection.Rtl),

    text_9PX_12SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ).copy(textDirection = TextDirection.Rtl),

    text_9PX_12SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_10PX_13SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ).copy(textDirection = TextDirection.Rtl),

    text_10PX_13SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_11PX_15SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_11PX_15SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_12PX_16SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_12PX_16SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_13PX_17SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_13PX_17SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_14PX_19SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_14PX_19SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_15PX_20SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_15PX_20SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp

    ).copy(textDirection = TextDirection.Rtl),
    text_16PX_21SP_B = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp
    ).copy(textDirection = TextDirection.Rtl),
    text_16PX_21SP_M = TextStyle(
        fontFamily = DANA,
        fontWeight = FontWeight.Medium,
        fontSize = 21.sp
    ).copy(textDirection = TextDirection.Rtl)
)
private val shape = AppShape(
    container = RoundedCornerShape(Dimens.size_12),
    button = RoundedCornerShape(50)
)

private val background = AppBackground(
    color = Gray_Light_100,
    tonalElevation = Dimens.size_10
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = ripple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypographyScheme provides typography,
        LocalAppShape provides shape,
        LocalIndication provides rippleIndication,
        LocalAppBackground provides background,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypographyScheme.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val background: AppBackground
        @Composable get() = LocalAppBackground.current

    val dimens: Dimens
        get() = Dimens

}


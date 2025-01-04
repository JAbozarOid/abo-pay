package com.jabozaroid.abopay.feature.intro.model

import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.intro.R


data class IntroUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val introItems: List<IntroItemModel> = listOf(
        IntroItemModel(
            "ابو پی",
            "ابو پی یک اپلیکیشن پرداخت امن با امکانات متنوع و منحصر به فرد است",
            R.drawable.ic_iva_logo_diamond
        ),
        IntroItemModel(
            "خدمات منحصر به فرد",
            "در ابو پی به خدمات بی نظیری چون پرداخت عوارض خروج از کشور و قبوض دولتی دسترسی دارید",
            R.drawable.ic_exit_toll
        ),
        IntroItemModel(
            "خلافی خودرو",
            "ریز خلافی های خودرو را به صورت رایگان استعلام و آن ها را پرداخت کنید",
            R.drawable.ic_driving_penalty
        ),
    ),
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
): IViewState

data class IntroItemModel(
    val title:String,
    val description: String,
    val image: Int,
)

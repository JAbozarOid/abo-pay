package com.jabozaroid.abopay.feature.cardmanagement.mapper

import com.jabozaroid.abopay.core.common.util.CardUtil
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_COLOR_KEY_DOWN
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_COLOR_KEY_UP
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_ICON_KEY_COLOR
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_ICON_KEY_WHITE
import com.jabozaroid.abopay.core.common.util.CardUtil.BANK_TITLE_KEY
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.DestinationCard
import com.jabozaroid.abopay.core.domain.model.cardmanagement.result.MyCardsResultItem

/**
 * Created on 21,December,2024
 */

fun MyCardsResultItem.mapToUiModel(): Card {
    val cardInfo = CardUtil.getBankIconResId(this.primaryAccNo!!)
    return Card(
        ownerName = this.extraData,
        number = this.primaryAccNo,
        token = this.token ?: "",
        isDefault = this.isDefault ?: false,
        month = Month(".."),
        year = Year(".."),
        bankName = cardInfo[BANK_TITLE_KEY],
        colorUp = cardInfo[BANK_COLOR_KEY_UP],
        colorDown = cardInfo[BANK_COLOR_KEY_DOWN],
        defaultCardLogo = R.drawable.ic_card_design_system,
        icon = cardInfo[BANK_ICON_KEY_WHITE],
        isActiveToken = true
    )
}


fun DestinationCard.mapToUiModel() =
    SearchItemModel(
        id = id.toString(),
        title = title ?: "",
        subTitle = pan ?: "",
        icon = pan?.let {
            CardUtil.getBankIconResId(it)[BANK_ICON_KEY_COLOR]
        }
    )
import com.jabozaroid.abopay.core.designsystem.component.model.CVV2
import com.jabozaroid.abopay.core.designsystem.component.model.Card
import com.jabozaroid.abopay.core.designsystem.component.model.Month
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.component.model.Year
import com.jabozaroid.abopay.feature.cardmanagement.R
import com.jabozaroid.abopay.feature.cardmanagement.model.MyCardOptionsModel

val cardsActionList = listOf(
    MyCardOptionsModel(
        id = 0,
        com.jabozaroid.abopay.core.common.R.string.edit,
        null,
        R.drawable.action_edit,
        true
    ),
    MyCardOptionsModel(
        id = 1,
        com.jabozaroid.abopay.core.common.R.string.block_card,
        com.jabozaroid.abopay.core.common.R.string.block_card_subtitle,
        R.drawable.action_block,
        false
    ),
    MyCardOptionsModel(
        id = 2,
        com.jabozaroid.abopay.core.common.R.string.copy_card,
        null,
        R.drawable.action_copy,
        true
    ),
    MyCardOptionsModel(
        id = 3,
        com.jabozaroid.abopay.core.common.R.string.default_title,
        null,
        R.drawable.action_default,
        true
    ),
    MyCardOptionsModel(
        id = 4,
        com.jabozaroid.abopay.core.common.R.string.authenticate,
        null,
        R.drawable.action_authenticate,
        false
    ),
    MyCardOptionsModel(
        id = 5,
        com.jabozaroid.abopay.core.common.R.string.delete,
        null,
        R.drawable.action_delete,
        true
    ),
)

val mockedUserCardList: List<Card> = mutableListOf(
    Card(
        ownerName = "محمد حسینی",
        number = "6037997175607630",
        token = "asfdsdf",
        cvv2 = CVV2(number = "456"),
        month = Month(number = "09"),
        year = Year("09"),
        bankName = com.jabozaroid.abopay.core.common.R.string.melli_bank,
        colorUp = com.jabozaroid.abopay.core.common.R.color.melli_up,
        colorDown = com.jabozaroid.abopay.core.common.R.color.melli_Down,
        defaultCardLogo = R.drawable.default_icon,
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_white_melli,
        isActiveToken = true
    ),
    Card(
        ownerName = "محمد حسینی",
        number = "5029081077672174",
        token = "asfdsdf",
        cvv2 = CVV2(number = "14587"),
        month = Month(number = "02"),
        year = Year("06"),
        bankName = com.jabozaroid.abopay.core.common.R.string.tosee_bank,
        colorUp = com.jabozaroid.abopay.core.common.R.color.tosee_taavon_up,
        colorDown = com.jabozaroid.abopay.core.common.R.color.tosee_taavon_down,
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_white_tosee,
        isActiveToken = true
    ),
    Card(
        ownerName = "محمد حسینی",
        number = "6104338921525206",
        token = "asfdsdf",
        cvv2 = CVV2(number = "963"),
        month = Month(number = "10"),
        year = Year("03"),
        bankName = com.jabozaroid.abopay.core.common.R.string.mellat_bank,
        colorUp = com.jabozaroid.abopay.core.common.R.color.melat_up,
        colorDown = com.jabozaroid.abopay.core.common.R.color.melat_down,
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_white_mellat

    ),
    Card(
        ownerName = "محمد حسینی",
        number = "6219861800662511",
        token = "asfdsdf",
        cvv2 = CVV2(number = "6598"),
        month = Month(number = "11"),
        year = Year("10"),
        bankName = com.jabozaroid.abopay.core.common.R.string.saman_bank,
        colorUp = com.jabozaroid.abopay.core.common.R.color.saman_up,
        colorDown = com.jabozaroid.abopay.core.common.R.color.saman_down,
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_white_saman,
        isDefault = true
    ),
    Card(
        ownerName = "محمد حسینی",
        number = "5894631120785399",
        token = "asfdsdf",
        cvv2 = CVV2(number = "6598"),
        month = Month(number = "11"),
        year = Year("09"),
        bankName = com.jabozaroid.abopay.core.common.R.string.refah_bank,
        colorUp = com.jabozaroid.abopay.core.common.R.color.refah_up,
        colorDown = com.jabozaroid.abopay.core.common.R.color.refah_down,
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_white_refah

    )
)

val searchItems = listOf(
    SearchItemModel(
        "1",
        title = "حسینی",
        subTitle = "6037997175607630",
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_color_melli
    ), SearchItemModel(
        "2",
        title = "موسوی",
        subTitle = "6037997175607630",
        icon = com.jabozaroid.abopay.core.common.R.drawable.card_icon_color_mellat
    ), SearchItemModel(
        "3",
        title = "غایب",
        subTitle = "5892101178084188",
        icon = com.jabozaroid.abopay.core.designsystem.R.drawable.ic_bank_saman
    )
)
package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.SearchItemModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

/**
 *  Created on 8/31/2024 
 **/

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    searchItems: List<SearchItemModel>,
    onItemSelect: (SearchItemModel) -> Unit,
    leftIcon: Int? = null,
    secondIcon: Int? = null,
    onLeftIconClicked: (SearchItemModel) -> Unit = {},
    onSecondIconClicked: (SearchItemModel) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        // content
        Content(
            searchItems = searchItems,
            leftIcon = leftIcon,
            onLeftIconClicked = onLeftIconClicked,
            onItemSelect = {
                onItemSelect.invoke(it)
            },
            onSecondIconClicked = onSecondIconClicked,
            secondIcon = secondIcon
        )
    }
}


@Composable
fun Content(
    modifier: Modifier = Modifier,
    searchItems: List<SearchItemModel>,
    onItemSelect: (SearchItemModel) -> Unit,
    leftIcon: Int?,
    secondIcon: Int?,
    onLeftIconClicked: (SearchItemModel) -> Unit,
    onSecondIconClicked: (SearchItemModel) -> Unit,
) {

    ListContent(
        modifier,
        searchItems,
        onItemSelect,
        leftIcon,
        secondIcon,
        onLeftIconClicked,
        onSecondIconClicked
    )
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    searchItems: List<SearchItemModel>,
    onItemSelect: (SearchItemModel) -> Unit = {},
    leftIcon: Int?,
    secondIcon: Int?,
    onLeftIconClicked: (SearchItemModel) -> Unit = {},
    onSecondIconClicked: (SearchItemModel) -> Unit = {},
) {


    Column(modifier = modifier) {
        var query by rememberSaveable { mutableStateOf("") }

        AppTextField(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.size_5,
                    horizontal = Dimens.size_12
                ),
            value = query,

            onValueChange = { query = it },
            placeHolder = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.search),
            placeHolderAlignment = Alignment.CenterEnd,
            leadingIcon = {
                Icon(
                    tint = AppTheme.colorScheme.aboDetailDots,
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "leading icon",
                    modifier = Modifier.size(Dimens.size_20)
                )
            }
        )

        val resultList = search(query = query.lowercase(), list = searchItems)
        LazyColumn(
            modifier = Modifier
                .padding(all = Dimens.size_5)
                .wrapContentSize()
        ) {

            items(count = resultList.size) { item ->
                ItemContainer(
                    item = resultList[item],
                    onItemSelect,
                    leftIcon,
                    secondIcon,
                    onLeftIconClicked,
                    onSecondIconClicked
                )
            }
        }
    }


}


@Composable
private fun ItemContainer(
    item: SearchItemModel,
    onItemClicked: (SearchItemModel) -> Unit,
    leftIcon: Int? = R.drawable.ic_delete,
    secondIcon: Int? = R.drawable.ic_edit_item,
    onLeftIconClicked: (SearchItemModel) -> Unit,
    onSecondIconClicked: (SearchItemModel) -> Unit,
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.size_5, vertical = Dimens.size_2
            )
            .clip(
                RoundedCornerShape(Dimens.size_12)
            )
            .background(AppTheme.colorScheme.aboDisableTextFieldOutline)
            .padding(
                horizontal = Dimens.size_5
            ),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftIcon?.let {
            Image(painter = painterResource(id = it),
                contentDescription = "icon",
                modifier = Modifier
                    .width(Dimens.size_34)
                    .height(Dimens.size_34)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onLeftIconClicked(item)
                    }
                    .padding(
                        start = Dimens.size_8,
                        end = Dimens.size_4,
                    ),
                alignment = Alignment.Center)
        }
        secondIcon?.let {
            Image(painter = painterResource(id = it),
                contentDescription = "secondIcon",
                modifier = Modifier
                    .width(Dimens.size_34)
                    .height(Dimens.size_34)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onSecondIconClicked(item)
                    }
                    .padding(
                        start = Dimens.size_8,
                        end = Dimens.size_4,
                    ),

                alignment = Alignment.Center)
        }

        Column(Modifier
            .padding(
                vertical = Dimens.size_4, horizontal = Dimens.size_8
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onItemClicked(item)
            }
            .weight(1f)) {


            Text(
                text = item.title.ifBlank { aboPayStringResource(id = com.jabozaroid.abopay.core.common.R.string.without_name) },
                style = AppTheme.typography.text_10PX_13SP_M.copy(
                    fontWeight = FontWeight.Bold,
                    textDirection = TextDirection.Ltr

                ),
                modifier = Modifier
                    .padding(
                        start = Dimens.size_4, top = Dimens.size_4
                    )
                    .align(Alignment.End),
                color = AppTheme.colorScheme.aboTitleText,
                textAlign = TextAlign.Right,

                )


            Text(
                modifier = Modifier
                    .padding(
                        bottom = Dimens.size_5, start = Dimens.size_4
                    )
                    .align(Alignment.End),
                text = item.subTitle,
                style = AppTheme.typography.text_10PX_13SP_B.copy(
                    textDirection = TextDirection.Ltr
                ),
                color = AppTheme.colorScheme.aboTitleText,
                textAlign = TextAlign.Right,
            )


        }

        item.icon?.let {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = "icon",
                modifier = Modifier
                    .width(Dimens.size_40)
                    .height(Dimens.size_40)
                    .padding(Dimens.size_8),
                alignment = Alignment.Center
            )
        }


    }


}


@Composable
fun search(query: String, list: List<SearchItemModel>): List<SearchItemModel> {

    val foundItems = mutableListOf<SearchItemModel>()
    for (item in list) {
        if (item.title.lowercase().startsWith(query) || item.subTitle.startsWith(query)) {
            foundItems.add(item)
        }
    }
    return foundItems
}


@Preview(showBackground = true)
@ThemePreviews
@Composable
fun PreviewSearchComponent() {

    val item = listOf(
        SearchItemModel(
            "1", title = "حسینی", subTitle = "6037997175607630", icon = R.drawable.ic_abo_pay
        ), SearchItemModel(
            "2", title = "موسوی", subTitle = "6037997175607630", icon = R.drawable.abo_pay_toolbar_logo
        ), SearchItemModel(
            "3", title = "غایب", subTitle = "5892101178084188", icon = R.drawable.abo_pay_toolbar_logo
        )
    )
    AppTheme {
        AppBackground(modifier = Modifier) {
            SearchComponent(
                searchItems = item,
                onItemSelect = { },
                leftIcon = R.drawable.ic_delete,
                onLeftIconClicked = {}
            )
        }
    }
}






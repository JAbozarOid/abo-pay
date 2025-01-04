package com.jabozaroid.abopay.core.designsystem.component



import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.UserAccountUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalUserAccounts(
    modifier: Modifier,
    accounts: List<UserAccountUiModel>,
    onIbanIconClicked: (accountIndex: Int) -> Unit,
    onAccountNumberIconClicked: (accountIndex: Int) -> Unit,
    onBalanceIconClicked: (accountIndex: Int) -> Unit,
    onSettingIconClicked: (accountIndex: Int) -> Unit
) {

    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { accounts.size })

    HorizontalPager(
        modifier = modifier
            .padding(horizontal = Dimens.size_15),
        state = pagerState,
        pageSpacing = Dimens.size_8,
    ) {

        val coroutineScope = rememberCoroutineScope()

        UserAccount(
            accounts[it],
            onLeftIconClicked = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it - 1)
                }
            },
            onRightArrowClicked = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it + 1)
                }
            },
            onAccountNumberIconClicked = {
                onAccountNumberIconClicked.invoke(it)
            },
            onBalanceIconClicked = {
                onBalanceIconClicked.invoke(it)
            },
            onIbanIconClicked = {
                onIbanIconClicked.invoke(it)
            },
            onSettingIconClicked = {
                onSettingIconClicked.invoke(it)
            }
        )

    }
}

@Composable
private fun UserAccount(
    account: UserAccountUiModel,
    onRightArrowClicked: () -> Unit = {},
    onLeftIconClicked: () -> Unit = {},
    onIbanIconClicked: () -> Unit,
    onAccountNumberIconClicked: () -> Unit,
    onBalanceIconClicked: () -> Unit,
    onSettingIconClicked: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(Dimens.size_10, 180.dp)
            .fillMaxHeight()
    ) {

        val (accountRef) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(accountRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .animateContentSize()
                .fillMaxWidth()
                .heightIn(Dimens.size_10, 180.dp)
                .fillMaxHeight(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = Dimens.size_1,
                disabledElevation = Dimens.size_1
            )
        ) {

            Surface(
                modifier = Modifier.fillMaxSize(),
                shadowElevation = Dimens.size_1,
                tonalElevation = Dimens.size_1,
                color = AppTheme.colorScheme.secondary
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppTheme.colorScheme.secondary,
                                        AppTheme.colorScheme.secondary.copy(alpha = 0.8F),
                                        AppTheme.colorScheme.secondary
                                    ),
                                )
                            )
                            .paint(
                                painterResource(id = R.drawable.pattern_card),
                                contentScale = ContentScale.FillBounds,
                                alpha = 0.1F
                            )
                    )

                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val (
                            leftArrowRef,
                            rightArrowRef,
                            nameRef,
                            accountNumberRef,
                            ibanRef,
                            balanceRef,
                            settingRef,
                            billRef
                        ) = createRefs()

                        Image(
                            modifier = Modifier
                                .constrainAs(leftArrowRef) {
                                    start.linkTo(parent.start, Dimens.size_8)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.value(Dimens.size_8)
                                    height = Dimension.value(Dimens.size_8)
                                }
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onLeftIconClicked.invoke() },
                            painter = painterResource(id = R.drawable.ic_double_arrow_left),
                            contentDescription = "left arrow",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        Image(
                            modifier = Modifier
                                .constrainAs(rightArrowRef) {
                                    end.linkTo(parent.end, Dimens.size_8)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.value(Dimens.size_8)
                                    height = Dimension.value(Dimens.size_8)
                                }
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onRightArrowClicked.invoke() },
                            painter = painterResource(id = R.drawable.ic_double_arrow_right),
                            contentDescription = "left arrow",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        AccountRowInfo(
                            modifier = Modifier.constrainAs(nameRef) {
                                end.linkTo(rightArrowRef.start, Dimens.size_8)
                                top.linkTo(parent.top, Dimens.size_40)
                                start.linkTo(settingRef.end)
                                width = Dimension.fillToConstraints
                            }, title = account.name,
                            icon = R.drawable.ic_mobile_bank
                        )

                        AccountRowInfo(
                            modifier = Modifier.constrainAs(accountNumberRef) {
                                top.linkTo(nameRef.bottom, Dimens.size_8)
                                end.linkTo(rightArrowRef.start, Dimens.size_8)
                                start.linkTo(settingRef.end)
                                width = Dimension.fillToConstraints
                            },
                            title = account.accountNumber,
                            icon = R.drawable.ic_copy,
                            onIconClicked = onAccountNumberIconClicked
                        )

                        AccountRowInfo(
                            modifier = Modifier.constrainAs(ibanRef) {
                                top.linkTo(accountRef.bottom, Dimens.size_8)
                                end.linkTo(rightArrowRef.start, Dimens.size_8)
                                start.linkTo(settingRef.end)
                                width = Dimension.fillToConstraints
                            },
                            title = account.iban,
                            icon = R.drawable.ic_copy,
                            onIconClicked = onIbanIconClicked
                        )

                        AccountRowInfo(
                            modifier = Modifier.constrainAs(balanceRef) {
                                top.linkTo(ibanRef.bottom)
                                end.linkTo(rightArrowRef.start, Dimens.size_8)
                                bottom.linkTo(parent.bottom)
                            },
                            title = account.balance ?: "مانده حساب",
                            icon = R.drawable.ic_eye,
                            onIconClicked = onBalanceIconClicked
                        )

                        Image(
                            modifier = Modifier
                                .constrainAs(settingRef) {
                                    top.linkTo(parent.top, Dimens.size_34)
                                    start.linkTo(parent.start, Dimens.size_8)
                                }
                                .size(Dimens.size_25, Dimens.size_25)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    onSettingIconClicked.invoke()
                                },
                            painter = painterResource(id = R.drawable.ic_setting),
                            contentDescription = "ic setting",
                            colorFilter = ColorFilter.tint(AppTheme.colorScheme.onSecondary)
                        )

                        AppOutlinedButton(
                            contentPadding = PaddingValues(
                                horizontal = Dimens.size_10,
                                vertical = Dimens.size_4
                            ),
                            modifier = Modifier.constrainAs(billRef) {
                                start.linkTo(parent.start, Dimens.size_8)
                                bottom.linkTo(parent.bottom, Dimens.size_34)
                            },
                            onClick = { },
                            borderColor = AppTheme.colorScheme.onSecondary
                        ) {
                            ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_M) {
                                Text(
                                    text = "صورت حساب",
                                    color = AppTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }

                }
            }
        }

    }
}

@Composable
private fun AccountRowInfo(
    modifier: Modifier,
    title: String,
    icon: Int,
    onIconClicked: (() -> Unit)? = null
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        ProvideTextStyle(value = AppTheme.typography.text_9PX_12SP_B) {
            Text(text = title)
            Spacer(modifier = Modifier.width(Dimens.size_16))
            Image(
                modifier = Modifier
                    .size(Dimens.size_20, Dimens.size_20)
                    .padding(Dimens.size_2)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onIconClicked?.invoke()
                    },
                painter = painterResource(id = icon), contentDescription = "account info $title",
                colorFilter = ColorFilter.tint(AppTheme.colorScheme.onSecondary)
            )
        }
    }

}

@ThemePreviews
@Composable
fun PreviewUserAccounts() {

    AppTheme {
        val userAccounts = listOf(
            UserAccountUiModel(
                name = "حساب قرض الحسنه",
                accountNumber = "0307160114004",
                id = "1",
                iban = "IR030716011400400000000",
                balance = "100,000,000"
            ),
            UserAccountUiModel(
                name = "حساب جاری",
                accountNumber = "0307160114004",
                id = "2",
                iban = "IR030716011400400000000"
            ),
        )

        HorizontalUserAccounts(
            modifier = Modifier.fillMaxWidth(),
            accounts = userAccounts,
            onSettingIconClicked = {},
            onIbanIconClicked = {},
            onBalanceIconClicked = {},
            onAccountNumberIconClicked = {}
        )

    }

}


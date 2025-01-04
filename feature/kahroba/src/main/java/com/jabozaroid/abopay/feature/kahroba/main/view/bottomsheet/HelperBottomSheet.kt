package com.jabozaroid.abopay.feature.kahroba.main.view.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.BottomSheetComponent
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.kahroba.R
import com.jabozaroid.abopay.feature.kahroba.main.model.HelperItem

@Composable
fun HelperComponentBottomSheet(
    helperItem: List<HelperItem>,
    onClickUnderstandButton: () -> Unit = {},
) {

    BottomSheetComponent(
        title = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.helper_title),
        isBtn1Enabled = true,
        onDismiss = onClickUnderstandButton,
        onBtn1Click = onClickUnderstandButton,
        btn1Text = aboPayStringResource(com.jabozaroid.abopay.core.common.R.string.understand_button_title),
        content = {
            HelperBottomSheetContent(
                helperItem = helperItem,
            )
        }, dualActionBtn = false,
        isScrollable = false
    )

}

@Composable
private fun HelperBottomSheetContent(
    helperItem: List<HelperItem>,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.size_20,
                    vertical = Dimens.size_8
                )
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colorScheme.background),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.mipmap.kahroba_helper_img),
            contentDescription = "helperImage"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.size_20,
                    vertical = Dimens.size_8
                )
        ) {
            items(items = helperItem,
                key = { item -> item.index }) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.size_16),
                    ) {

                        Box(
                            Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(AppTheme.colorScheme.kahrobaHelperCircle),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = it.index.toString(),
                                style = AppTheme.typography.text_12PX_16SP_M.copy(
                                    fontWeight = FontWeight(700), textAlign = TextAlign.Center
                                ),
                                color = AppTheme.colorScheme.ivaTitleText,
                            )
                        }

                        Spacer(modifier = Modifier.width(Dimens.size_16))
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = it.description,
                            color = AppTheme.colorScheme.ivaTitleText,
                            style = AppTheme.typography.text_10PX_13SP_M.copy(
                                fontWeight = FontWeight(500),

                                ),
                            lineHeight = 24.sp
                        )

                    }
                }
            }
        }

    }

}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun Preview() {
    AppTheme {
        AppBackground(modifier = Modifier) {
            HelperBottomSheetContent(
                listOf(
                    HelperItem(
                        index = 1,
                        description = "ابتدا اطلاعات کارت بانکی خود را در اپلیکشن ثبت کنید. این کار فقط برای یک\u200Cبار انجام خواهد شد."
                    ),
                    HelperItem(
                        index = 2,
                        description = "پس از آن در زمان خرید از فروشگاه کافی است مبلغ کالا توسط خود شما یا فروشنده در دستگاه کارتخوان (مشابه آنچه تاکنون انجام می\u200Cدادید) ثبت شود."
                    ),
                    HelperItem(
                        index = 3,
                        description = "بعد از این مرحله رمز کارت خود را در دستگاه کارتخوان وارد کنید و پس از آن تلفن همراه هوشمند خود را به دستگاه کارتخوان نزدیک کنید."
                    ),
                    HelperItem(
                        index = 4,
                        description = "با نزدیک\u200Cشدن موبایل شما به دستگاه کارتخوان، چون اطلاعات شما قبلا در اپلیکیشن مرتبط با کهربا ثبت شده، بلافاصله تراکنش انجام و طبق روال معمول یک پرینت کاغذی صورتحساب صادر می\u200Cشود."
                    )
                )
            )
        }
    }
}

package com.jabozaroid.abopay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jabozaroid.abopay.core.designsystem.R
import com.jabozaroid.abopay.core.designsystem.component.model.FeatureUiModel
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Blue_Dark_50
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens


@Composable
fun HorizontalFeatureList(
    modifier: Modifier,
    name: String = "",
    containerColor: Color = AppTheme.colorScheme.background,
    features: List<FeatureUiModel>,
    actionIcon: Int = -1,
    onActionIconClicked: (() -> Unit)? = null,
    onFeatureClicked: (feature: FeatureUiModel) -> Unit,
) {

    Card(
        modifier = modifier
            .padding(Dimens.size_10),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

            val (
                headerImageRef,
                nameRef,
                iconRef,
                featureListRef
            ) = createRefs()

            Image(
                modifier = Modifier.constrainAs(headerImageRef) {
                    top.linkTo(parent.top, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                    width = Dimension.value(Dimens.size_10)
                    height = Dimension.value(Dimens.size_10)
                },
                painter = painterResource(id = R.drawable.ic_list_bullet),
                contentDescription = "headerImage",
                colorFilter = ColorFilter.tint(AppTheme.colorScheme.onBackground)
            )

            ProvideTextStyle(
                value = AppTheme.typography.text_9PX_12SP_B.copy(
                    fontWeight = FontWeight.Bold
                )
            ) {

                Text(
                    modifier = Modifier.constrainAs(nameRef) {
                        top.linkTo(parent.top, Dimens.size_8)
                        end.linkTo(headerImageRef.start, Dimens.size_8)
                        start.linkTo(iconRef.end, Dimens.size_8)
                        bottom.linkTo(headerImageRef.bottom)
                        width = Dimension.fillToConstraints

                    },
                    text = name,
                    color = Blue_Dark_50,
                    textAlign = TextAlign.End
                )

            }

            Image(
                modifier = Modifier.constrainAs(iconRef) {
                    start.linkTo(parent.start, Dimens.size_8)
                    top.linkTo(parent.top, Dimens.size_8)
                    width = Dimension.value(Dimens.size_20)
                    height = Dimension.value(Dimens.size_20)
                },
                painter = painterResource(id = actionIcon),
                contentDescription = "actionIcon",
                colorFilter = ColorFilter.tint(Blue_Dark_50)
            )

            LazyRow(
                contentPadding = PaddingValues(Dimens.size_5),
                modifier = Modifier.constrainAs(featureListRef) {
                    top.linkTo(nameRef.bottom, Dimens.size_34)
                    bottom.linkTo(parent.bottom, Dimens.size_8)
                    start.linkTo(parent.start, Dimens.size_34)
                    end.linkTo(parent.end, Dimens.size_34)
                }
            ) {

                items(
                    count = features.size
                ) {

                    if (it == 0)
                        Spacer(modifier = Modifier.width(Dimens.size_4))

                    FeatureItem(
                        modifier = Modifier.size(60.dp, 60.dp),
                        feature = features[it]
                    )
                    Spacer(modifier = Modifier.width(Dimens.size_4))
                }

            }

        }
    }

}

@Composable
private fun FeatureItem(modifier: Modifier, feature: FeatureUiModel) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(Dimens.size_20, Dimens.size_20),
                painter = painterResource(id = feature.logo),
                contentDescription = "feature logo"
            )
            Spacer(modifier = Modifier.height(Dimens.size_16))
            ProvideTextStyle(
                value = AppTheme.typography.text_9PX_12SP_B
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = feature.title,
                    color = AppTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}

@ThemePreviews
@Composable
fun PreviewHorizontalFeatureList() {

    AppTheme {

        val features = listOf(
            FeatureUiModel(
                "1",
                "کارت به کارت",
            ),
            FeatureUiModel(
                "2",
                "خرید بیمه",
            ),
            FeatureUiModel(
                "3",
                "نشان بانک",
            ),
            FeatureUiModel(
                "4",
                "عوارض خروج",
            ),
            FeatureUiModel(
                "5",
                "اینترنت",
            ),
            FeatureUiModel(
                "6",
                "شارژ",
            ),
            FeatureUiModel(
                "7",
                "موجودی",
            ),
        )

//        AppBackground(modifier = Modifier) {
//
//        }

        Column(
            modifier = Modifier.padding(Dimens.size_10)
        ) {
            HorizontalFeatureList(
                actionIcon = R.drawable.ic_edit_item,
                name = "خدمات پرداخت",
                modifier = Modifier.fillMaxWidth(),
                features = features,
                onFeatureClicked = {})
        }


    }

}

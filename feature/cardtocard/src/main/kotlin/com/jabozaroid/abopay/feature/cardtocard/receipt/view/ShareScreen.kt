package com.jabozaroid.abopay.feature.cardtocard.receipt.view

import android.graphics.Picture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.wrapContent
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.cardtocard.receipt.model.ReceiptUiModel

/**
 *  Created on 10/27/2024
 **/


@Composable
internal fun SharingReceipt(state: ReceiptUiModel, picture: Picture) {
    Column(
        modifier = Modifier
            .padding(Dimens.size_20)
            .fillMaxSize()
            .drawWithCache {
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()
                onDrawWithContent {
                    val pictureCanvas =
                        androidx.compose.ui.graphics.Canvas(
                            picture.beginRecording(
                                width,
                                height
                            )
                        )
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()

                    drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                }
            }
    ) {
        SharingContent(state = state)
    }
}

@Composable
internal fun SharingContent(
    state: ReceiptUiModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.ivaBackgroundScreen)
            .padding(
                top = Dimens.size_4,
                start = Dimens.size_8,
                end = Dimens.size_8,
                bottom = Dimens.size_4
            )
            .clip(RoundedCornerShape(Dimens.size_12))
            .background(AppTheme.colorScheme.background)
    ) {

        val (titleContainer, topMetaDataContainer, bottomMetaDataContainer) = createRefs()

        TitleReceiptContent(state = state, modifier = Modifier.constrainAs(titleContainer) {
            top.linkTo(parent.top, Dimens.size_34)
            start.linkTo(parent.start, Dimens.size_8)
            end.linkTo(parent.end, Dimens.size_8)
        })

        TopMetaDataContainer(
            modifier = Modifier
                .padding(
                    vertical = Dimens.size_4,
                    horizontal = Dimens.size_24
                )
                .constrainAs(topMetaDataContainer) {
                    top.linkTo(titleContainer.bottom, Dimens.size_8)
                    start.linkTo(parent.start, Dimens.size_8)
                    end.linkTo(parent.end, Dimens.size_8)
                    bottom.linkTo(bottomMetaDataContainer.top, Dimens.size_8)
                    height = wrapContent
                }, state = state
        )

        BottomMetaDataContainer(Modifier.constrainAs(bottomMetaDataContainer) {
            top.linkTo(topMetaDataContainer.bottom, Dimens.size_8)
            start.linkTo(parent.start, Dimens.size_8)
            end.linkTo(parent.end, Dimens.size_8)
            bottom.linkTo(parent.bottom, Dimens.size_8)
            height = fillToConstraints
        }, state)

    }

}

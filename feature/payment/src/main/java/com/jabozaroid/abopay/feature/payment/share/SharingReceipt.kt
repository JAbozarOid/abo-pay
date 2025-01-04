package com.jabozaroid.abopay.feature.payment.share

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
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.feature.payment.reciept.model.ReceiptUiModel
import com.jabozaroid.abopay.feature.payment.reciept.view.component.MetaDataReceiptContainer
import com.jabozaroid.abopay.feature.payment.reciept.view.component.TitleReceiptContent


/**
 * Created on 18,September,2024
 */

@Composable
fun SharingReceipt(state: ReceiptUiModel, picture: Picture) {
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
fun SharingContent(
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
        val (titleContainer, metaDataContainer) = createRefs()

        TitleReceiptContent(state = state, modifier = Modifier.constrainAs(titleContainer) {
            top.linkTo(parent.top, Dimens.size_8)
            start.linkTo(parent.start, Dimens.size_8)
            end.linkTo(parent.end, Dimens.size_8)
        })

        MetaDataReceiptContainer(Modifier.constrainAs(metaDataContainer) {
            top.linkTo(titleContainer.bottom, Dimens.size_8)
            start.linkTo(parent.start, Dimens.size_8)
            end.linkTo(parent.end, Dimens.size_8)
            bottom.linkTo(parent.bottom, Dimens.size_8)
            height = fillToConstraints
        }, state)

    }

}
package com.jabozaroid.abopay.feature.finndow.view.shadowing

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.core.common.util.aboPayStringResource
import com.jabozaroid.abopay.core.designsystem.component.AppToolbar
import com.jabozaroid.abopay.core.designsystem.component.ThemePreviews
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthAction
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthEvent
import com.jabozaroid.abopay.feature.finndow.model.auth.FinndowAuthUiModel
import com.jabozaroid.abopay.feature.finndow.util.FormatUtil.Companion.formatTime
import com.jabozaroid.abopay.feature.finndow.view.shadowing.components.AudioPlayer
import com.jabozaroid.abopay.feature.finndow.view.shadowing.components.RecordButton
import com.jabozaroid.abopay.feature.finndow.viewmodel.FinndowAuthViewModel
import kotlinx.coroutines.delay

class FinndowShadowingScreen : BaseScreen<FinndowAuthUiModel, FinndowAuthAction, FinndowAuthEvent>(
    route = ApplicationRoutes.FINNDOW_SHADOWING_SCREEN_ROUTE, name = "FinndowShadowingScreen"
) {
    @Composable
    override fun ViewModel(): FinndowAuthViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FinndowAuthUiModel) {
        val viewModel = ViewModel()
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
            MainContent(
                onContinueBtnClicked = {
                    viewModel.process(FinndowAuthAction.OnContinueClicked)
                }
            )
        }
    }

    @Composable
    private fun MainContent(
        onContinueBtnClicked: () -> Unit = {}
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.aboBackgroundScreen)
        ) {

            val (toolbarRef, content) = createRefs()
            AppToolbar(
                rightIcon = null,
                textStyle = TextStyle(
                    fontFamily = AppTheme.typography.text_48PX_24SP_B.fontFamily,
                    fontSize = AppTheme.typography.text_48PX_24SP_B.fontSize
                ),
                modifier = Modifier
                    .constrainAs(toolbarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = Dimens.size_8)
                    .fillMaxWidth(),
                toolbarTitle = aboPayStringResource(id = R.string.shadowing_courses),
                onRightIconClicked = {}
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.size_4,
                        start = Dimens.size_8,
                        end = Dimens.size_8,
                        bottom = Dimens.size_8
                    )
                    .clip(RoundedCornerShape(Dimens.size_12))
                    .background(AppTheme.colorScheme.background)
                    .constrainAs(
                        content
                    ) {
                        top.linkTo(toolbarRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    }) {

                // Red finnish exercises
                Text(
                    text = aboPayStringResource(R.string.finnish_exercises),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.finndowRedBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = Dimens.size_12, top = Dimens.size_12)
                )

                Spacer(modifier = Modifier.height(Dimens.size_16))

                // Dark rounded card with finish text
                Card(
                    shape = RoundedCornerShape(Dimens.size_16),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Dimens.size_12),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(Dimens.size_16)
                    ) {
                        Text(
                            text = """
                        Hei! Nimeni on Anna. Olen kotoisin Helsingistä
                    """.trimIndent(),
                            style = AppTheme.typography.text_14PX_19SP_M.copy(
                                textDirection = TextDirection.Ltr
                            ),
                            color = AppTheme.colorScheme.aboWhiteBackground,
                            textAlign = TextAlign.Start,

                            )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.size_16))

                // Red translated in english
                Text(
                    text = aboPayStringResource(R.string.translate_in_english),
                    style = AppTheme.typography.text_12PX_16SP_M,
                    color = AppTheme.colorScheme.finndowRedBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = Dimens.size_12)
                )

                // Dark rounded card with english translated text
                Card(
                    shape = RoundedCornerShape(Dimens.size_16),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Dimens.size_12),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(Dimens.size_16)
                    ) {
                        Text(
                            text = """
                        Hi! My name is Anna. I am from Helsinki..
                    """.trimIndent(),
                            style = AppTheme.typography.text_14PX_19SP_M.copy(
                                textDirection = TextDirection.Ltr
                            ),
                            color = AppTheme.colorScheme.aboWhiteBackground,
                            textAlign = TextAlign.Start,

                            )
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.size_16))
                AudioPlayer()
                Spacer(modifier = Modifier.height(Dimens.size_16))
                RecordButton()
            }
        }
    }






    @Preview(showBackground = true)
    @ThemePreviews
    @Composable
    fun PreviewFinndowHomeScreen() {
        AppTheme {
            MainContent()
        }
    }
}


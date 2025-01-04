package com.jabozaroid.abopay.core.designsystem.component.loading

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jabozaroid.abopay.core.designsystem.R

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.loading_animation
        )
    )

    val preloaderProgress = animateLottieCompositionAsState(
        composition = preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition.value,
        progress = preloaderProgress.value,
        modifier = modifier
    )
}
package com.jabozaroid.abopay.feature.finndow.view.shadowing.components

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jabozaroid.abopay.core.common.R
import com.jabozaroid.abopay.feature.finndow.util.FormatUtil.Companion.formatTime
import kotlinx.coroutines.delay

@Composable
internal fun AudioPlayer() {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.finn) }

    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var currentTime by remember { mutableStateOf(0) }
    val totalDuration = mediaPlayer.duration

    // Update progress every second
    LaunchedEffect(isPlaying) {
        val handler = Handler(Looper.getMainLooper())
        val updateProgress = object : Runnable {
            override fun run() {
                if (isPlaying) {
                    val currentPos = mediaPlayer.currentPosition
                    currentTime = currentPos
                    progress = currentPos / totalDuration.toFloat()
                    handler.postDelayed(this, 500)
                }
            }
        }

        if (isPlaying) handler.post(updateProgress)

        try {
            // Keep the coroutine alive while playing
            while (isPlaying) {
                delay(1000)
            }
        } finally {
            // Cleanup code when LaunchedEffect is canceled
            handler.removeCallbacks(updateProgress)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Timer
        Text(
            text = "${formatTime(currentTime)} / ${formatTime(totalDuration)}",
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        // Progress bar
        Slider(
            value = progress,
            onValueChange = { newValue ->
                progress = newValue
                mediaPlayer.seekTo((newValue * totalDuration).toInt())
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Play/Pause button
        IconButton(onClick = {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                isPlaying = false
            } else {
                mediaPlayer.start()
                isPlaying = true
            }
        }) {
            Icon(
                painter = painterResource(
                    id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                ),
                contentDescription = "Play or Pause",
                modifier = Modifier.size(48.dp)
            )
        }
    }

    // Clean up MediaPlayer when Composable is destroyed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
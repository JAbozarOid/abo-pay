package com.jabozaroid.abopay.core.ui.helper

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.staticCompositionLocalOf
import com.jabozaroid.abopay.core.ui.entity.DisplayedError


val LocalSnackBarState = staticCompositionLocalOf<SnackbarHostState> {
    error("snackbar not provided")
}


suspend fun SnackbarHostState.showSnackbar(
    displayedError: DisplayedError.SnackBarError,
    onResult: ((result: SnackbarResult) -> Unit)? = null,
) {
    val result = showSnackbar(
        displayedError.displayedMessage,
    )
    onResult?.invoke(result)
}

fun Context.showToast(displayedError: DisplayedError.ToastError) {
    Toast.makeText(this, displayedError.displayedMessage, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message : String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
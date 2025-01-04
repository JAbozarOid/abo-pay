package com.jabozaroid.abopay.core.common.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Created on 09,November,2024
 */

@Composable
fun (@receiver:StringRes Int)?.aboPayStringResource(): String? =
    if (this != null) stringResource(id = this) else null

@Composable
fun aboPayStringResource(@StringRes id: Int): String =
    stringResource(id = id)

@Composable
fun aboPayStringResource(@StringRes id: Int, vararg formatArgs: Any): String =
    stringResource(id, *formatArgs)

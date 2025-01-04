package com.jabozaroid.abopay.core.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.core.ui.viewmodel.BaseViewModel


@Composable
fun <STATE : IViewState> BaseViewModel<STATE, *, *>.getComposableState(): State<STATE> {
    return this.uiStateFlow.collectAsStateWithLifecycle(initialValue = this.initialState)
}
package com.jabozaroid.abopay.core.ui.model

import com.jabozaroid.abopay.core.designsystem.component.SnackState


interface IEvent {
    data class ShowSnackMessage(
        val message: String,
        val snackState: SnackState = SnackState.MESSAGE,
    ) : IEvent

    data class ShowSnack(val message: Int, val snackState: SnackState = SnackState.MESSAGE) : IEvent
    data class ShowToast(val message: Int) : IEvent
}
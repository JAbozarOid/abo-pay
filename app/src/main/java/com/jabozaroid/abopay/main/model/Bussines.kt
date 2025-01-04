package com.jabozaroid.abopay.main.model

import com.jabozaroid.abopay.core.ui.model.IAction
import com.jabozaroid.abopay.core.ui.model.IEvent


internal sealed interface MainAction : IAction {
    data object FetchStartUpData : MainAction
}


internal sealed interface MainEvent : IEvent
package com.jabozaroid.abopay.feature.intro.model

import com.jabozaroid.abopay.core.ui.model.IAction


sealed interface IntroAction : IAction {

    data object EntryButtonClicked : IntroAction

}
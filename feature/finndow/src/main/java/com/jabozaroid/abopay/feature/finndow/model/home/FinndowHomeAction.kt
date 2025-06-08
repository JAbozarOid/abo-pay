package com.jabozaroid.abopay.feature.finndow.model.home

import com.jabozaroid.abopay.core.ui.model.IAction

sealed interface FinndowHomeAction : IAction {
    data object OnRequestShadowingPractice : FinndowHomeAction
    data object OnShadowingCoursesBtnClicked : FinndowHomeAction
    data object NavigateUp : FinndowHomeAction
    data object OnNextPracticeBtnClicked : FinndowHomeAction
    data object OnRequestGetShadowingResult : FinndowHomeAction
    data object OnRequestShadowingCourses : FinndowHomeAction
    data class OnRequestShadowingCoursesDetail(val courseDetail : String) : FinndowHomeAction
}
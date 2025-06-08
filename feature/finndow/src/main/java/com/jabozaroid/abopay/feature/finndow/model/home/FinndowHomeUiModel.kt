package com.jabozaroid.abopay.feature.finndow.model.home

import androidx.compose.ui.graphics.Color
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState

data class FinndowHomeUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val shadowingUiModel: ShadowingUiModel = ShadowingUiModel(),
    val shadowingCoursesUiModel: ShadowingCoursesUiModel = ShadowingCoursesUiModel(),
    val shadowingCoursesDetailUiModel: ShadowingCoursesDetailUiModel = ShadowingCoursesDetailUiModel()
) : IViewState

data class ShadowingUiModel(
    val finnishText: String? = "",
    val pronunciationGuide: String? = "",
    val englishTranslation: String? = "",
    val type: String? = "",
    val level: String? = ""
)

data class ShadowingCoursesUiModel(
    val shadowingCourses: List<ShadowingCoursesName> = emptyList()
)

data class ShadowingCoursesName(
    val courseIndex: Int,
    val courseName: String,
    val courseColor: Color,

    )

data class ShadowingCoursesDetailUiModel(
    val courseDetail: String = "",
    val courseLevel: String = ""
) {
    fun getCourseDetail(courseDetail: String): List<String> {
        val levels = listOf("A1", "A2", "B1", "B2", "C1", "C2")
        return levels.map { level -> "$courseDetail $level" }
    }
}

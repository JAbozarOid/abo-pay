package com.jabozaroid.abopay.core.network.dataSource.finndow.mapper

import com.jabozaroid.abopay.core.domain.model.finndow.result.ShadowingPracticeResult
import com.jabozaroid.abopay.core.network.model.finndow.ShadowingPracticeNetworkResult

fun ShadowingPracticeNetworkResult.mapToShadowingPracticeDomainModel(): ShadowingPracticeResult =
    ShadowingPracticeResult(
        finnishText = this.finnishText,
        pronunciationGuide = this.pronunciationGuide,
        englishTranslation = this.englishTranslation
    )
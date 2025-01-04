package com.jabozaroid.abopay.feature.internetSelection.model

import com.jabozaroid.abopay.core.designsystem.component.model.IconItemUiModel
import com.jabozaroid.abopay.core.domain.AboPayExceptionMessage
import com.jabozaroid.abopay.core.domain.AboPayServerError
import com.jabozaroid.abopay.core.ui.model.IViewState
import com.jabozaroid.abopay.feature.internet.model.InternetCatalogUIModel
import com.jabozaroid.abopay.feature.internet.model.InternetDurationType

data class InternetSelectionUiModel(
    override val loading: Boolean = false,
    override val hasError: Boolean = false,
    override val aboPayException: AboPayExceptionMessage = AboPayExceptionMessage(),
    override val aboPayApiError: AboPayServerError = AboPayServerError(),
    val internetCatalogList: Map<InternetDurationType, List<InternetCatalogUIModel>> = mapOf(),
    val operatorUiModels: List<IconItemUiModel> = listOf(),
) : IViewState

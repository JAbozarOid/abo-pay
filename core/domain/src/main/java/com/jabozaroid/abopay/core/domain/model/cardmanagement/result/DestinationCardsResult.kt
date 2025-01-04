package com.jabozaroid.abopay.core.domain.model.cardmanagement.result

data class DestinationCardsResult(
    val destinationCards: List<DestinationCard>,
)

data class DestinationCard(
    val id: Int? = null,
    val pan: String? = null,
    val title: String? = null,
    val isDeleted: Boolean? = null,
)
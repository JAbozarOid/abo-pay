package com.jabozaroid.abopay.feature.internet.model

import com.jabozaroid.abopay.core.designsystem.component.model.ServiceItem

/**
 * Created on 16,October,2024
 */

data class InternetCatalogUIModel(
    val title: String,
    val internetCatalogList: List<InternetCatalog>
)

data class InternetCatalog(
    val name: String = "",
    val amount: Double = 0.0,
)

fun List<ServiceItem>.mapToInternetCatalogUIModel(simType: String): Map<InternetDurationType, List<InternetCatalogUIModel>> {
    val groupedCatalogs = this
        .filter { service ->
            service.name?.contains(simType) ?: true
        }
        .sortedBy { service ->
            service.durationCode?.toInt()
        }.groupBy { service ->
            service.durationCode
        }

    val dailyList = mutableListOf<InternetCatalogUIModel>()
    groupedCatalogs.filterKeys {
        it?.toInt()!! < 7
    }
        .forEach { item ->
            dailyList.add(
                InternetCatalogUIModel(
                title = item.value[0].durationTitle ?: "",
                internetCatalogList = item.value.map {
                    InternetCatalog(
                        name = it.name ?: "",
                        amount = it.amount ?: 0.0
                    )
                }
            )
            )
        }

    val weeklyList = mutableListOf<InternetCatalogUIModel>()
    groupedCatalogs.filterKeys {
        it?.toInt() in 7..29
    }
        .forEach { item ->
            weeklyList.add(
                InternetCatalogUIModel(
                title = item.value[0].durationTitle ?: "",
                internetCatalogList = item.value.map {
                    InternetCatalog(
                        name = it.name ?: "",
                        amount = it.amount ?: 0.0
                    )
                }
            )
            )
        }

    val monthlyList = mutableListOf<InternetCatalogUIModel>()
    groupedCatalogs.filterKeys {
        it?.toInt()!! > 29
    }
        .forEach { item ->
            monthlyList.add(
                InternetCatalogUIModel(
                title = item.value[0].durationTitle ?: "",
                internetCatalogList = item.value.map {
                    InternetCatalog(
                        name = it.name ?: "",
                        amount = it.amount ?: 0.0
                    )
                }
            )
            )
        }

    return mapOf(
        Pair(InternetDurationType.DAILY, dailyList),
        Pair(InternetDurationType.WEEKLY, weeklyList),
        Pair(InternetDurationType.MONTHLY, monthlyList),
    )
}

enum class InternetDurationType {
    DAILY, WEEKLY, MONTHLY
}

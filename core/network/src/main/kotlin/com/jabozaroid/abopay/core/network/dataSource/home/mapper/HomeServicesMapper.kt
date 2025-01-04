package com.jabozaroid.abopay.core.network.dataSource.home.mapper

import com.jabozaroid.abopay.core.domain.model.home.Category
import com.jabozaroid.abopay.core.domain.model.home.CategoryItem
import com.jabozaroid.abopay.core.domain.model.home.HomeService
import com.jabozaroid.abopay.core.network.model.home.HomeServiceNetWorkResult

/**
 * Created on 31,August,2024
 */

fun HomeServiceNetWorkResult.mapToDomainModel() = HomeService(
    categories = categories.map { category ->
        Category(
            backgroundColor = category.backgroundColor,
            description = category.description,
            id = category.id,
            isActive = category.isActive,
            title = category.title,
            categoryItems = category.categoryItems.map { categoryItem ->
                CategoryItem(
                    appType = categoryItem.appType,
                    categoryId = categoryItem.categoryId,
                    iconName = categoryItem.iconName,
                    id = categoryItem.id,
                    isActive = categoryItem.isActive,
                    title = categoryItem.title,
                    url = categoryItem.url
                )
            },
        )
    }

)

package com.jabozaroid.abopay.core.domain.model.home

/**
 * Created on 31,August,2024
 */

data class HomeService(
    val categories: List<Category>
)

data class Category(
    val backgroundColor: String,
    val categoryItems: List<CategoryItem>,
    val description: String,
    val id: Int,
    val isActive: Boolean,
    val title: String
)

data class CategoryItem(
    val appType: Int,
    val categoryId: Int,
    val iconName: String,
    val id: Int,
    val isActive: Boolean,
    val title: String,
    val url: String
)

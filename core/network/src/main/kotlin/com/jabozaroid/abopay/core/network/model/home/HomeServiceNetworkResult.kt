package com.jabozaroid.abopay.core.network.model.home

/**
 * Created on 31,August,2024
 */

data class HomeServiceNetWorkResult(
    val categories: List<CategoryNetWorkResult>
)

data class CategoryNetWorkResult(
    val backgroundColor: String,
    val categoryItems: List<CategoryItemNetWorkResult>,
    val description: String,
    val id: Int,
    val isActive: Boolean,
    val title: String
)

data class CategoryItemNetWorkResult(
    val appType: Int,
    val categoryId: Int,
    val iconName: String,
    val id: Int,
    val isActive: Boolean,
    val title: String,
    val url: String
)
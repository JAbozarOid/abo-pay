package com.jabozaroid.abopay.core.domain.model.charge.result.favourite

data class FavouriteChargeNumberResult(
    val items: List<Item> = listOf()
)

data class Item(
    val phoneNumber: String,
    val ownerPhoneNumber: String,
    val icon : Int
)

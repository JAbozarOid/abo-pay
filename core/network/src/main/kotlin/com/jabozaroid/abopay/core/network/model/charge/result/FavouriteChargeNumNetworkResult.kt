package com.jabozaroid.abopay.core.network.model.charge.result

data class FavouriteChargeNumNetworkResult(
    val favoriteMobileNumbers: List<Item> = listOf()
)

data class Item(
    val phoneNumber: String,
    val ownerPhoneNumber: String
)


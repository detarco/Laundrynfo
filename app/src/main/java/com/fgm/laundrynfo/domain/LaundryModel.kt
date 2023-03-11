package com.fgm.laundrynfo.domain

data class ClientModel(
    val id: Int,
    val name: String,
    val surname: String,
    val phone: Int,
    val email: String,
    val address: String
)

data class CustomerModel(
    val id: Int,
    val name: String,
    val surname: String,
    val phone: Int,
    val email: String,
    val address: String,
    val items: List<ItemModel>
)

data class ItemModel(
    val id: Int,
    val name: String
)
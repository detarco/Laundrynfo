package com.fgm.laundrynfo.domain

data class ClientModel(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val telephone: Int,
    val nif: String
)

data class CustomerModel(
    val id: Int,
    val name: String,
    val surname: String,
    val items: List<ItemModel>
)

data class ItemModel(
    val id: Int,
    val name: String
)
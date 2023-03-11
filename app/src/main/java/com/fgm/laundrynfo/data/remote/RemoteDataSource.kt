package com.fgm.laundrynfo.data.remote

import com.fgm.laundrynfo.domain.CustomerModel
import com.fgm.laundrynfo.domain.ItemModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RemoteDataSource : RemoteRepository {

    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }

    override suspend fun getClientsAndItems(): List<CustomerModel> {
        val customerList = mutableListOf<CustomerModel>()

        val dataSnapshot = database
            .child("customers")
            .awaitQueryValue()

        dataSnapshot.children.forEach { ds ->
            ds.getValue(RemoteCustomerModel::class.java)?.let { remoteCustomer ->

                val itemList = mutableListOf<ItemModel>()
                remoteCustomer.customer_items.forEach { remoteItems ->
                    itemList.add(RemoteItemModel.toModel(remoteItems))
                }

                customerList.add(
                    CustomerModel(
                        remoteCustomer.customer_id,
                        remoteCustomer.customer_name,
                        remoteCustomer.customer_surname,
                        itemList
                    )
                )
            }
        }
        return customerList
    }

    override suspend fun addClientsAndItems(customerModel: CustomerModel) {

        val remoteItemList: List<RemoteItemModel> = customerModel.items.map {
            RemoteItemModel(
                it.id,
                it.name
            )
        }

        val remoteCustomer = RemoteCustomerModel(
            customerModel.id,
            customerModel.name,
            customerModel.surname,
            remoteItemList
        )

        database
            .child("customers")
            .child("customer_" + customerModel.id.toString())
            .setValue(remoteCustomer)

    }

    override suspend fun delCustomerAndItems(customerId: Int) {
        database
            .child("customers")
            .child("customer_$customerId")
            .removeValue()
    }

}
package com.fgm.laundrynfo.data.remote

import com.fgm.laundrynfo.domain.CustomerModel

interface RemoteRepository {

    suspend fun getClientsAndItems(): List<CustomerModel>

    suspend fun addClientsAndItems(customerModel: CustomerModel)


    suspend fun delCustomerAndItems(customerId: Int)

}
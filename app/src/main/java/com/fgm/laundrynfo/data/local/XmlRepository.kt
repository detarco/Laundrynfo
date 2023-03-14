package com.fgm.laundrynfo.data.local

import com.fgm.laundrynfo.domain.CustomerModel


interface XmlRepository {

    //Guardar un listado de clientes (al menos 10 clientes).
    fun saveCustomers(customerList: List<CustomerModel>)

    //Recuperar un listado de clientes.
    fun getCustomers(): List<CustomerModel>

    //Recuperar un cliente en concreto.
    fun getCustomer(customerID: Int): CustomerModel

    //Eliminar un cliente en concreto.
    fun delCustomer(customerID: Int)

    //Modificar los datos de un cliente (nombre, apellidos, email o teléfono)
    fun updCustomer(customerModel: CustomerModel)

}
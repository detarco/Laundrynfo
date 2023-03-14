package com.fgm.laundrynfo.data.local

import com.fgm.laundrynfo.domain.CustomerModel


interface XmlRepository {

    //Guardar un listado de clientes (al menos 10 clientes).
    fun saveClients(customerList: List<CustomerModel>)

    //Recuperar un listado de clientes.
    fun getClients(): List<CustomerModel>

    //Recuperar un cliente en concreto.
    fun getClient(customerID: Int): CustomerModel

    //Eliminar un cliente en concreto.
    fun delClient(customerID: Int)

    //Modificar los datos de un cliente (nombre, apellidos, email o teléfono)
    fun updClient(customerModel: CustomerModel)

}
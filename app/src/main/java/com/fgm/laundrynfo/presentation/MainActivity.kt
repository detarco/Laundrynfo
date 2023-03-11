package com.fgm.laundrynfo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fgm.laundrynfo.R
import com.fgm.laundrynfo.data.local.FileDataRepository
import com.fgm.laundrynfo.data.local.FileRepository
import com.fgm.laundrynfo.data.local.XmlDataRepository
import com.fgm.laundrynfo.data.local.XmlRepository
import com.fgm.laundrynfo.data.remote.RemoteDataSource
import com.fgm.laundrynfo.data.remote.RemoteRepository
import com.fgm.laundrynfo.domain.ClientModel
import com.fgm.laundrynfo.domain.CustomerModel
import com.fgm.laundrynfo.domain.ItemModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkFile()
    }


    private val fileRepository: FileRepository = FileDataRepository(this, Gson())
    private val xmlRepository: XmlRepository = XmlDataRepository(this, Gson())
    private val remoteRepository: RemoteRepository = RemoteDataSource()

    private fun checkFile() {
        checkSaveCustomers()
        checkFindCustomer(1)
        checkDeleteCustomer(2)
        checkUpdateCustomer(3)
    }

    private fun checkSaveCustomers() {
        val mockClientList = buildClients()
        fileRepository.saveClients(mockClientList)
        val clientList = fileRepository.getClients()
        Log.d("dev_list", "$clientList")

        xmlRepository.saveClients(mockClientList)
        val clientListXml = xmlRepository.getClients()
        Log.d("dev_xml_list", "$clientListXml")

        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.addClientsAndItems(
                CustomerModel(
                    5,
                    "Demo 5",
                    "Surdemo 5",
                    mutableListOf(
                        ItemModel(
                            5,
                            "Item 5"
                        )
                    )
                )
            )
        }
    }

    private fun checkFindCustomer(customerId: Int) {
        val thatClient = fileRepository.getClient(customerId)
        Log.d("dev_find_customer", "$thatClient")

        val thatClientXml = xmlRepository.getClient(customerId)
        Log.d("dev_xml_find_customer", "$thatClientXml")

        CoroutineScope(Dispatchers.IO).launch {
            val clientList = remoteRepository.getClientsAndItems()
            Log.d("dev_remote", "$clientList")
        }

    }

    private fun checkDeleteCustomer(customerId: Int) {
        fileRepository.delClient(customerId)
        val clientList = fileRepository.getClients()
        Log.d("dev_list_after_delete", "$clientList")

        xmlRepository.delClient(customerId)
        val clientListXml = xmlRepository.getClients()
        Log.d("dev_xml_after_delete", "$clientListXml")

        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.delCustomerAndItems(7)
        }
    }


    private fun checkUpdateCustomer(customerId: Int) {
        fileRepository.updClient(
            ClientModel(
                customerId,
                "demo_name",
                "demo_surname",
                "demo@gmail.com",
                123123123,
                "S12312312"
            )
        )
        val clientList = fileRepository.getClients()
        Log.d("dev_updated", "$clientList")

        xmlRepository.updClient(
            ClientModel(
                customerId,
                "name",
                "surname",
                "demo@gmail.com",
                123123123,
                "S12312312"
            )
        )
        val clientListXml = xmlRepository.getClients()
        Log.d("dev_xml_updated", "$clientListXml")
    }

    private fun buildClients(): List<ClientModel> {
        return mutableListOf(
            ClientModel(
                1,
                "Cliente 1",
                "Apellido Cliente 1",
                "cliente1@gmail.com",
                123123123,
                "A12312312"
            ),
            ClientModel(
                2,
                "Cliente 2",
                "Apellido Cliente 2",
                "cliente2@gmail.com",
                123123123,
                "B12312312"
            ),
            ClientModel(
                3,
                "Cliente 3",
                "Apellido Cliente 3",
                "cliente3@gmail.com",
                123123123,
                "C12312312"
            ),
            ClientModel(
                4,
                "Cliente 4",
                "Apellido Cliente 4",
                "cliente4@gmail.com",
                123123123,
                "D12312312"
            ),
            ClientModel(
                5,
                "Cliente 5",
                "Apellido Cliente 5",
                "cliente5@gmail.com",
                123123123,
                "E12312312"
            ),
            ClientModel(
                6,
                "Cliente 6",
                "Apellido Cliente 6",
                "cliente6@gmail.com",
                123123123,
                "F12312312"
            ),
            ClientModel(
                7,
                "Cliente 7",
                "Apellido Cliente 7",
                "cliente7@gmail.com",
                123123123,
                "G12312312"
            ),
            ClientModel(
                8,
                "Cliente 8",
                "Apellido Cliente 8",
                "cliente8@gmail.com",
                123123123,
                "H12312312"
            ),
            ClientModel(
                9,
                "Cliente 9",
                "Apellido Cliente 9",
                "cliente9@gmail.com",
                123123123,
                "I12312312"
            ),
            ClientModel(
                10,
                "Cliente 10",
                "Apellido Cliente 10",
                "cliente10@gmail.com",
                123123123,
                "J12312312"
            )
        )
    }
}
package com.fgm.laundrynfo.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fgm.laundrynfo.R
import com.fgm.laundrynfo.data.local.XmlDataRepository
import com.fgm.laundrynfo.data.local.XmlRepository
import com.fgm.laundrynfo.data.remote.RemoteDataSource
import com.fgm.laundrynfo.data.remote.RemoteRepository
import com.fgm.laundrynfo.domain.AdapterCustomerModel
import com.fgm.laundrynfo.domain.AdapterItemModel
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

        val recyclerView: RecyclerView = findViewById(R.id.recyclerId)
        val adapterCustomerModel = AdapterCustomerModel(this, (getXmlSave()))
        val adapterItemModel = AdapterItemModel(this, getXmlItemsSave())

        //Config recycler view
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterCustomerModel

        findViewById<Button>(R.id.theButton)
            .setOnClickListener {
                addCustomer()
                crutch()
                adapterCustomerModel.update(getXmlSave())
            }
    }

    private val xmlRepository: XmlRepository = XmlDataRepository(this, Gson())
    private val remoteRepository: RemoteRepository = RemoteDataSource()

    private fun checkFile() {
        /**checkCustomers()
        addCustomer()
        checkDeleteCustomer(2)*/
    }

    private fun crutch(): List<CustomerModel> {
        val crutchList = mutableListOf<CustomerModel>()
        CoroutineScope(Dispatchers.IO).launch {
            checkCustomers().map { cm ->
                crutchList.add(
                    CustomerModel(
                        cm.id,
                        cm.name,
                        cm.surname,
                        cm.phone,
                        cm.address,
                        cm.email,
                        cm.items.map { im ->
                            ItemModel(
                                im.id,
                                im.name
                            )
                        }
                    )
                )
                xmlRepository.updCustomer(cm)
                Log.d("dev_crutch", "$cm")
            }
        }
        return crutchList
    }

    private fun getXmlSave(): List<CustomerModel> {
        return xmlRepository.getCustomers()
    }

    private fun getXmlItemsSave(): List<List<ItemModel>> {
        return xmlRepository.getCustomers().map { it.items }
    }

    private fun changeButton(): List<CustomerModel> {

        var customerList = mutableListOf<CustomerModel>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getClientsAndItems().map { cM ->
                customerList.add(
                    CustomerModel(
                        cM.id,
                        cM.name,
                        cM.surname,
                        cM.phone,
                        cM.email,
                        cM.address,
                        cM.items.map { iM ->
                            ItemModel(
                                iM.id,
                                iM.name
                            )
                        }
                    )
                )
            }
        }

        Log.d("dev_debug", "It worked?")
        return customerList
    }

    private fun checkCustomers(): List<CustomerModel> {
        val clientList = mutableListOf<CustomerModel>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getClientsAndItems().map {
                clientList.add(it)
                xmlRepository.updCustomer(it)
                Log.d("dev_customer_model", "$it")
            }
        }
        return clientList
    }

    private fun addCustomer() {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.addClientsAndItems(
                CustomerModel(
                    7,
                    "Demo 7",
                    "Surdemo 7",
                    658,
                    "something@something.com",
                    "democasa658",
                    mutableListOf(
                        ItemModel(
                            7,
                            "Item 7"
                        )
                    )
                )
            )
        }
    }

    /**
    private fun checkFindCustomer(customerId: Int) {

    CoroutineScope(Dispatchers.IO).launch {
    remoteRepository.getClientsAndItems().first { it.id == customerId }

    }

    }
     */
    private fun checkDeleteCustomer(customerId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.delCustomerAndItems(customerId)
        }
    }

    /**
    private fun checkUpdateCustomer(customerId: Int) {
    fileRepository.updClient(
    ClientModel(
    customerId,
    "demo_name",
    "demo_surname",
    123123123,
    "demo@gmail.com",
    "demoaddress1"
    )
    )
    val clientList = fileRepository.getClients()
    Log.d("dev_updated", "$clientList")

    xmlRepository.updClient(
    ClientModel(
    customerId,
    "name",
    "surname",
    123123123,
    "demo@gmail.com",
    "address2"
    )
    )
    val clientListXml = xmlRepository.getClients()
    Log.d("dev_xml_updated", "$clientListXml")
    }
     **/
}
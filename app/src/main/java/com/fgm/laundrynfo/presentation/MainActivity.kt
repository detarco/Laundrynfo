package com.fgm.laundrynfo.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fgm.laundrynfo.R
import com.fgm.laundrynfo.data.remote.RemoteDataSource
import com.fgm.laundrynfo.data.remote.RemoteRepository
import com.fgm.laundrynfo.domain.AdapterMoyai
import com.fgm.laundrynfo.domain.CustomerModel
import com.fgm.laundrynfo.domain.ItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkFile()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerId)
        var adapterMoyai = AdapterMoyai(this, crutch())

        //Config recycler view
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterMoyai

        findViewById<Button>(R.id.thebutton)
            .setOnClickListener {
                adapterMoyai = AdapterMoyai(this, crutch())
            }
    }

    private val remoteRepository: RemoteRepository = RemoteDataSource()

    private fun checkFile() {
        /**checkCustomers()
        addCustomer()*/
        checkDeleteCustomer(2)
    }

    private fun crutch(): List<CustomerModel> {
        val crutchList = mutableListOf<CustomerModel>()
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
            Log.d("dev_crutch", "$cm")
        }
        return crutchList
    }

    private fun changeButton() {
        val customerList = checkCustomers()
        findViewById<TextView>(R.id.simple_quote_tv).text = "$customerList"
    }

    private fun checkCustomers(): List<CustomerModel> {
        val clientList = mutableListOf<CustomerModel>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.getClientsAndItems().map {
                clientList.add(it)
                Log.d("dev_customer_model", "$it")
            }
        }
        return clientList
    }

    private fun addCustomer() {
        CoroutineScope(Dispatchers.IO).launch {
            remoteRepository.addClientsAndItems(
                CustomerModel(
                    6,
                    "Demo 6",
                    "Surdemo 6",
                    345,
                    "something@something.com",
                    "democasa456",
                    mutableListOf(
                        ItemModel(
                            6,
                            "Item 6"
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
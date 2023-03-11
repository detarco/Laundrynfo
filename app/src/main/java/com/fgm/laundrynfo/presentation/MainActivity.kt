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

        val recyclerView : RecyclerView = findViewById(R.id.recyclerId)
        val adapterMoyai : AdapterMoyai = AdapterMoyai()

        //Config adapter
        //adapterMoyai.customerList
        adapterMoyai.recyclerViewAdapter(
            buildDemo()
            ,this)
        //Config recycler view
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterMoyai


        findViewById<Button>(R.id.thebutton)
            .setOnClickListener{
                val customerList = checkCustomers().map {
                    findViewById<TextView>(R.id.simple_quote_tv).text = "Hello"
                }
            }
    }

    private val remoteRepository: RemoteRepository = RemoteDataSource()

    private fun checkFile() {
        checkCustomers()
        addCustomer()
        checkDeleteCustomer(2)
    }

    private fun buildDemo():List<CustomerModel>{
        val customerList = mutableListOf<CustomerModel>()
        customerList.add(
            CustomerModel(
                1,
                "name1",
                "surname1",
                123,
                "email1",
                "address1",
                mutableListOf<ItemModel>(
                    ItemModel(
                        1,
                        "item1"
                    )
                )
            )
        )
        customerList.add(
            CustomerModel(
                2,
                "name2",
                "surname2",
                345,
                "email2",
                "address2",
                mutableListOf<ItemModel>(
                    ItemModel(
                        2,
                        "item2"
                    )
                )
            )
        )
        return customerList
    }

    private fun changeButton(){
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
                    5,
                    "Demo 5",
                    "Surdemo 5",
                    124,
                    "something@something.com",
                    "democasa123",
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
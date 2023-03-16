package com.fgm.laundrynfo.presentation

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        checkCustomers()

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
            }
    }

    private val xmlRepository: XmlRepository = XmlDataRepository(this, Gson())
    private val remoteRepository: RemoteRepository = RemoteDataSource()

    private val etCustomerNewId by lazy {
        findViewById<EditText>(R.id.newIdNumber)
    }
    private val etNewCustomerName by lazy {
        findViewById<EditText>(R.id.newCustomerName)
    }
    private val etNewCustomerSurname by lazy {
        findViewById<EditText>(R.id.newCustomerSurname)
    }
    private val etNewCustomerPhone by lazy {
        findViewById<EditText>(R.id.newPhone)
    }
    private val etNewCustomerEmail by lazy {
        findViewById<EditText>(R.id.newEmail)
    }
    private val etNewCustomerAddress by lazy {
        findViewById<EditText>(R.id.newAddress)
    }

    private val intNewCustomerId by lazy {
        etCustomerNewId.text.toString()
    }
    private val strNetCustomerName by lazy {
        etNewCustomerName.text.toString()
    }
    private val strNewCustomerSurname by lazy {
        etNewCustomerSurname.text.toString()
    }
    private val intNewCustomerPhone by lazy {
        etNewCustomerPhone.text.toString()
    }
    private val strNewCustomerEmail by lazy {
        etNewCustomerEmail.text.toString()
    }
    private val strNewCustomerAddress by lazy {
        etNewCustomerAddress.text.toString()
    }

    private fun getXmlSave(): List<CustomerModel> {
        return xmlRepository.getCustomers()
    }

    private fun getXmlItemsSave(): List<List<ItemModel>> {
        return xmlRepository.getCustomers().map { it.items }
    }

    private fun changeButton(): List<CustomerModel> {

        val customerList = mutableListOf<CustomerModel>()
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

        if (etCustomerNewId.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Id", Toast.LENGTH_SHORT).show()
        } else if (etNewCustomerName.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Name", Toast.LENGTH_SHORT).show()
        } else if (etNewCustomerSurname.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Surname", Toast.LENGTH_SHORT).show()
        } else if (etNewCustomerPhone.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Phone", Toast.LENGTH_SHORT).show()
        } else if (etNewCustomerEmail.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Email", Toast.LENGTH_SHORT).show()
        } else if (etNewCustomerAddress.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Needs a new Address", Toast.LENGTH_SHORT).show()
        } else {
            val newCustomerModel = CustomerModel(
                intNewCustomerId.toInt(),
                strNetCustomerName,
                strNewCustomerSurname,
                intNewCustomerPhone.toInt(),
                strNewCustomerEmail,
                strNewCustomerAddress,
                mutableListOf()
            )
            xmlRepository.updCustomer(
                newCustomerModel
            )
            Toast.makeText(
                this,
                "New Customer with id $newCustomerModel.id added",
                Toast.LENGTH_LONG
            ).show()

            CoroutineScope(Dispatchers.IO).launch {
                remoteRepository.addClientsAndItems(
                    newCustomerModel
                )
            }
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

}
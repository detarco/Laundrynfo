package com.fgm.laundrynfo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.fgm.laundrynfo.domain.ClientModel
import com.fgm.laundrynfo.domain.CustomerModel

import com.google.gson.Gson
import kotlinx.coroutines.Job

class XmlDataRepository(
    val context: Context,
    private val gson: Gson
) : XmlRepository {


    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            "xml_clientList.xml", Context.MODE_PRIVATE
        )
    }

    override fun saveClients(customerList: List<CustomerModel>) {
        val edit = sharedPrefs.edit()
        customerList.map {
            edit.putString(
                it.id.toString(), gson.toJson(it, CustomerModel::class.java)
            )
            edit.apply()
        }
    }

    override fun getClients(): List<CustomerModel> {
        return sharedPrefs.all.values.map {
            gson.fromJson((it as String), CustomerModel::class.java)
        }
    }

    override fun getClient(customerID: Int): CustomerModel {
        return gson.fromJson(
            sharedPrefs.getString(customerID.toString(), "{}"), CustomerModel::class.java
        )
    }

    override fun delClient(customerID: Int) {
        val edit = sharedPrefs.edit()
        edit.remove(customerID.toString())
        edit.apply()
    }

    override fun updClient(customerModel: CustomerModel) {
        sharedPrefs.edit()
            .putString(customerModel.id.toString(), gson.toJson(customerModel, CustomerModel::class.java))
            .apply()
    }

}

package com.fgm.laundrynfo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.fgm.laundrynfo.domain.ClientModel

import com.google.gson.Gson

class XmlDataRepository(
    val context: Context,
    private val gson: Gson
) : XmlRepository {


    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            "xml_clientList.xml", Context.MODE_PRIVATE
        )
    }

    override fun saveClients(clientList: List<ClientModel>) {
        val edit = sharedPrefs.edit()
        clientList.map {
            edit.putString(
                it.id.toString(), gson.toJson(it, ClientModel::class.java)
            )
            edit.apply()
        }
    }

    override fun getClients(): List<ClientModel> {
        return sharedPrefs.all.values.map {
            gson.fromJson((it as String), ClientModel::class.java)
        }
    }

    override fun getClient(clientID: Int): ClientModel {
        return gson.fromJson(
            sharedPrefs.getString(clientID.toString(), "{}"), ClientModel::class.java
        )
    }

    override fun delClient(clientID: Int) {
        val edit = sharedPrefs.edit()
        edit.remove(clientID.toString())
        edit.apply()
    }

    override fun updClient(clientModel: ClientModel) {
        sharedPrefs.edit()
            .putString(clientModel.id.toString(), gson.toJson(clientModel, ClientModel::class.java))
            .apply()
    }

}

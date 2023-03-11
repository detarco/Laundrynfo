package com.fgm.laundrynfo.data.local

import android.content.Context
import com.fgm.laundrynfo.domain.ClientModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

class FileDataRepository(
    val context: Context,
    private val gson: Gson
) : FileRepository {

    private fun getFileCheck(): File {
        val file = File(context.filesDir, "file_Clients.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        return file

    }

    private fun saveClient(client: ClientModel) {
        val clientJson = gson.toJson(client)
        getFileCheck().appendText("\n" + clientJson)
    }

    override fun saveClients(clientList: List<ClientModel>) {
        val clientListJson = gson.toJson(clientList)
        getFileCheck().writeText(
            clientListJson
        )
    }

    override fun getClients(): List<ClientModel> {
        val clientList = getFileCheck().readText()
        val collectionType: Type = object : TypeToken<List<ClientModel?>?>() {}.type
        return gson.fromJson(clientList, collectionType)
    }

    override fun getClient(clientID: Int): ClientModel {
        val clientList = getClients()
        return clientList.first { it.id == clientID }
    }

    override fun delClient(clientID: Int) {
        val clientList = getClients()
        val clientListNoClient = mutableListOf<ClientModel>()
        clientList.map {
            if (it.id != clientID) {
                clientListNoClient.add(it)
            }
        }
        saveClients(clientListNoClient)
    }

    override fun updClient(clientModel: ClientModel) {
        val clientList = getClients()
        val clientListUpdated = mutableListOf<ClientModel>()
        clientList.map {
            if (it.id == clientModel.id) {
                delClient(it.id)
            } else {
                clientListUpdated.add(it)
            }
        }
        clientListUpdated.add(clientModel)
        saveClients(clientListUpdated)
    }

}
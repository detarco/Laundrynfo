package com.fgm.laundrynfo.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fgm.laundrynfo.R

class AdapterItemModel(
    val context: Context,
    private var itemList: List<List<ItemModel>>
) : RecyclerView.Adapter<AdapterItemModel.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemId: TextView
        val itemName: TextView

        init {

            itemId = view.findViewById(R.id.itemId)
            itemName = view.findViewById(R.id.itemName)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewitems, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemId.text = itemList[position].map { it.id }.toString()
        holder.itemName.text = itemList[position].map { it.name }.toString()
    }

    override fun getItemCount() = itemList.size

}
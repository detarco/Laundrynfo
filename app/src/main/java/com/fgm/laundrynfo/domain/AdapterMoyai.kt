package com.fgm.laundrynfo.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fgm.laundrynfo.R

class AdapterMoyai(
    val context: Context,
    private var customerList: List<CustomerModel>
) : RecyclerView.Adapter<AdapterMoyai.ViewHolder>() {

    class ViewHolder(view: View, view2: View) : RecyclerView.ViewHolder(view) {
        val customerId: TextView
        val customerName: TextView
        val customerSurname: TextView
        val customerPhone: TextView
        val customerEmail: TextView
        val customerAddress: TextView

        val itemId: TextView
        val itemName: TextView

        init {
            customerId = view.findViewById(R.id.customerId)
            customerName = view.findViewById(R.id.customerName)
            customerSurname = view.findViewById(R.id.customerSurname)
            customerPhone = view.findViewById(R.id.customerPhone)
            customerEmail = view.findViewById(R.id.customerEmail)
            customerAddress = view.findViewById(R.id.customerAddress)

            itemId = view2.findViewById(R.id.itemId)
            itemName = view2.findViewById(R.id.itemName)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlist, parent, false)
        val view2 =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewitems, parent, false)
        return ViewHolder(view, view2)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customerId.text = customerList[position].id.toString()
        holder.customerName.text = customerList[position].name
        holder.customerSurname.text = customerList[position].surname
        holder.customerPhone.text = customerList[position].phone.toString()
        holder.customerEmail.text = customerList[position].email
        holder.customerAddress.text = customerList[position].address

        holder.itemId.text = customerList[position].items.map {
            it.id
        }.toString()
        holder.itemName.text = customerList[position].items.map {
            it.name
        }.toString()

    }

    override fun getItemCount() = customerList.size

    fun update(customers: List<CustomerModel>){
        customerList = mutableListOf<CustomerModel>()
        customers.map {
            (customerList as MutableList<CustomerModel>).add(it)
        }
    }

}
package com.fgm.laundrynfo.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fgm.laundrynfo.R

class AdapterMoyai : RecyclerView.Adapter<AdapterMoyai.ViewHolder>(){

    var customerList : List<CustomerModel> = ArrayList()
    lateinit var context: Context

    fun recyclerViewAdapter(customerList:List<CustomerModel>, context: Context){
        this.customerList = customerList
        this.context = context
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val customerId:TextView
        val customerName:TextView
        val customerSurname:TextView
        val customerPhone:TextView
        val customerEmail:TextView
        val customerAddress:TextView

        init {
            customerId = view.findViewById(R.id.customerId)
            customerName = view.findViewById(R.id.customerName)
            customerSurname = view.findViewById(R.id.customerSurname)
            customerPhone = view.findViewById(R.id.customerPhone)
            customerEmail = view.findViewById(R.id.customerEmail)
            customerAddress = view.findViewById(R.id.customerAddress)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterMoyai.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterMoyai.ViewHolder, position: Int) {
        holder.customerId.text = customerList[position].id.toString()
        holder.customerName.text = customerList[position].name
        holder.customerSurname.text = customerList[position].surname
        holder.customerPhone.text = customerList[position].phone.toString()
        holder.customerEmail.text = customerList[position].email
        holder.customerAddress.text = customerList[position].address
    }

    override fun getItemCount() = customerList.size

}
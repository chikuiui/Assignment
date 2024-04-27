package com.example.passwordmanagerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanagerapp.databinding.ListItemBinding
import com.example.passwordmanagerapp.fragments.MainFragmentDirections
import com.example.passwordmanagerapp.models.Account

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var list = listOf<Account>()

    fun setContentList(list : List<Account>){
        this.list = list
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        holder.binding.accountType.text = list[position].accountName

        holder.itemView.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToDetailsFragment(list[position])
            it.findNavController().navigate(directions)
        }
    }



    inner class MyViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
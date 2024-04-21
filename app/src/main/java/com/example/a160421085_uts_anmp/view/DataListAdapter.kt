package com.example.a160421085_uts_anmp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.a160421085_uts_anmp.R
import com.example.a160421085_uts_anmp.databinding.DataListItemBinding
import com.example.a160421085_uts_anmp.model.Data
import com.squareup.picasso.Picasso

class DataListAdapter(val dataList: ArrayList<Data>):RecyclerView.Adapter<DataListAdapter.DataViewHolder>() {
    class DataViewHolder(var binding: DataListItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    fun updateDataList(newDataList:List<Data>){
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = DataListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.txtID.text = dataList[position].id
        holder.binding.txtTitle.text = dataList[position].title
        holder.binding.txtGenre.text = dataList[position].genre
        Picasso.get().load(dataList[position].image).into(holder.binding.imageView6)

        holder.binding.detailBtn2.setOnClickListener(){
            val action = DataListFragmentDirections.actionDataDetail(dataList[position].id?:"",dataList[position].title?:"",dataList[position].genre?:"",dataList[position].platforms?:"",dataList[position].image?:"")
            Navigation.findNavController(it).navigate(action)
        }
    }
}
package com.thc.hiddensecrets.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.utils.ItemData

class MyAdapter(private val context: Context, private var data: MutableList<ItemData?>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewItem: ImageView = view.findViewById(R.id.imageViewItem)
        val textViewItem: TextView = view.findViewById(R.id.textViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        Glide.with(context)
            .load(item?.imageUrl)
            .placeholder(R.drawable.bg_button)
            .into(holder.imageViewItem)

        holder.textViewItem.text = item?.description
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: MutableList<ItemData?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()  // Notifica o adaptador para atualizar a lista
    }
}


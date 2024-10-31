package com.thc.hiddensecrets.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.thc.hiddensecrets.R
import com.bumptech.glide.Glide

class MyAdapter(private val context: Context, private val data: List<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // ViewHolder que irá manter as referências aos views do item
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewItem: ImageView = view.findViewById(R.id.imageViewItem)
        // ajustar o titulo que vem da api´para o titulo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Use Glide para carregar a imagem
        Glide.with(context)
            .load(data[position]) // Aqui, `data` deve conter URLs das imagens
            .placeholder(R.drawable.bg_button) // Imagem de placeholder enquanto a imagem é carregada
            .into(holder.imageViewItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

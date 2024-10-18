package com.thc.hiddensecrets.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thc.hiddensecrets.R


class DashboardActivity : AppCompatActivity() {

    lateinit var recyclerView2: RecyclerView
    private var data: List<String> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(com.thc.hiddensecrets.R.layout.activity_dashboard)

        val imageView = findViewById<ImageView>(com.thc.hiddensecrets.R.id.imageView4)

        val imageUrl = "https://www.primecursos.com.br/blog/wp-content/uploads/2022/12/analisando-os-graficos-01122022.jpg"
        // Substitua pela URL da sua imagem

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        recyclerView2 = findViewById(R.id.recyclerView2) // Certifique-se de ter um RecyclerView no seu layout
        recyclerView2.layoutManager = LinearLayoutManager(this)

        // Exemplo de URLs de imagens
        val imageUrls = listOf(
            "https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",
            "https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",
            "https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",
            "https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",
            "https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",
            "https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",
            "https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",
            "https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",
            // Adicione mais URLs conforme necessário
        )

        val myAdapter = MyAdapter(this, imageUrls)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = myAdapter


    }

}
package com.thc.hiddensecrets.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R


class DashboardActivity : AppCompatActivity() {

    lateinit var recyclerView2: RecyclerView
    private var data: List<String> = listOf()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(com.thc.hiddensecrets.R.layout.activity_dashboard)

        val imageViewProfile = findViewById<ImageView>(R.id.imageViewItemProfile)
        val imageView = findViewById<ImageView>(com.thc.hiddensecrets.R.id.imageView4)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomHome = bottom.menu.findItem(R.id.navigation_home)

        // Defina o clique para mudar a imagem
        imageViewProfile.setOnClickListener {
            // Mudar a imagem para outra (por exemplo, outro drawable)
            setContentView(R.layout.activity_profile)
        }

        bottomHome.setOnMenuItemClickListener {
            val intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            // Ação para o item "Home"
            true
        }

        val imageUrl = "https://www.primecursos.com.br/blog/wp-content/uploads/2022/12/analisando-os-graficos-01122022.jpg"
        // Substitua pela URL da sua imagem

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        recyclerView2 = findViewById(R.id.recyclerView2) // Certifique-se de ter um RecyclerView no seu layout
        recyclerView2.layoutManager = LinearLayoutManager(this)

        // Exemplo de URLs de imagens
        val imageUrls = listOf(
            "https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg",
            "https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg",
            "https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg",
            "https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg",
            "https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg",
            "https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg",
            // Adicione mais URLs conforme necessário
        )

        val myAdapter = MyAdapter(this, imageUrls)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = myAdapter


    }

}
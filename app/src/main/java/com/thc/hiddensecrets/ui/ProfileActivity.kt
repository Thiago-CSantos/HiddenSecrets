package com.thc.hiddensecrets.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.utils.ItemData

class ProfileActivity : AppCompatActivity() {
    private lateinit var recyclerView3: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomHome = bottom.menu.findItem(R.id.navigation_home)

        bottomHome.setOnMenuItemClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            true
        }

        recyclerView3 = findViewById(R.id.recyclerView3) // Certifique-se de ter um RecyclerView no seu layout
        recyclerView3.layoutManager = LinearLayoutManager(this)

        // Exemplo de URLs de imagens
        val imageUrls:MutableList<ItemData?> = mutableListOf(
            ItemData("https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",""),
            ItemData("https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",""),
            ItemData("https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",""),
            ItemData("https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",""),
            ItemData("https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",""),
            ItemData("https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",""),
            ItemData("https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",""),
            ItemData("https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",""),
            // Adicione mais URLs conforme necessário
        )

        val myAdapter = MyAdapter(this, imageUrls)
        recyclerView3.layoutManager = LinearLayoutManager(this)
        recyclerView3.adapter = myAdapter
    }
}
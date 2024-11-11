package com.thc.hiddensecrets.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.Service.ApiService
import com.thc.hiddensecrets.network.RetrofitClient
import com.thc.hiddensecrets.utils.ItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView2: RecyclerView
    private lateinit var myAdapter: MyAdapter

    private val retrofitClient by lazy {
        RetrofitClient(this).createService(ApiService::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_dashboard)

        val imageViewProfile = findViewById<ImageView>(R.id.imageViewItemProfile)
        val imageView = findViewById<ImageView>(R.id.imageView4)
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val sharedView = findViewById<EditText>(R.id.shared)

        // Configuração do RecyclerView e Adapter
        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(this, mutableListOf())
        recyclerView2.adapter = myAdapter

        sharedView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val textShared: String = sharedView.text.toString()
                CoroutineScope(Dispatchers.Main).launch {
                    val imageUrls = newsImages(textShared)
                    myAdapter.setData(imageUrls)
                }
                true
            } else {
                false
            }
        }

        imageViewProfile.setOnClickListener {
            setContentView(R.layout.activity_profile)
        }

        bottom.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                else -> false
            }
        }

        Glide.with(this)
            .load("https://www.primecursos.com.br/blog/wp-content/uploads/2022/12/analisando-os-graficos-01122022.jpg")
            .into(imageView)

        val initialQuery = sharedView.text.takeIf { it.isNotEmpty() } ?: "IBOVESPA"
        CoroutineScope(Dispatchers.Main).launch {
            val imageUrls = newsImages(initialQuery.toString())
            myAdapter.setData(imageUrls)
        }
    }

    private suspend fun newsImages(shared: String): MutableList<ItemData?> {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            val response = retrofitClient.news(shared, primeiroDiaDoMes(), "pt-br")
            val articles = response.body()?.articles
            val items = mutableListOf<ItemData?>()

            if (articles.isNullOrEmpty()) {
                items.add(
                    ItemData("https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg", "")
                )
                items.add(
                    ItemData("https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg", "")
                )
            } else {
                articles.forEach { article ->
                    items.add(ItemData(article.urlToImage, article.title ?: ""))
                }
            }
            return@async items
        }
        return deferred.await()
    }

    private fun primeiroDiaDoMes(): String {
        val hoje = LocalDate.now()
        val primeiroDia = hoje.withDayOfMonth(1)
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return primeiroDia.format(formato)
    }
}

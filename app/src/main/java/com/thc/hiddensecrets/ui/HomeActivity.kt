package com.thc.hiddensecrets.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.Service.ApiService
import com.thc.hiddensecrets.network.RetrofitClient
import com.thc.hiddensecrets.utils.ItemData
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {

    private lateinit var textValue: TextView
    lateinit var lineChart: LineChart
    lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private val retrofitClient by lazy {
        RetrofitClient(this).createService(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_navigator_view)

        textValue = findViewById<EditText>(R.id.text_value)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomTrovao = bottom.menu.findItem(R.id.navigation_dashboard)
        val bottomHome = bottom.menu.findItem(R.id.navigation_home)

        val imageView = findViewById<ImageView>(R.id.imageViewItem)

        // Navegação entre telas
        imageView.setOnClickListener {
            setContentView(R.layout.activity_profile)
        }

        bottomTrovao.setOnMenuItemClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            true
        }

        val rootView = findViewById<View>(android.R.id.content)
        rootView.setBackgroundColor(Color.parseColor("#171820"))
        lineChart = findViewById(R.id.chart)

        // Configuração do gráfico
        lineChart.setBackgroundColor(Color.TRANSPARENT)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        setValor()
        setData()

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val item = newsImages()
            myAdapter = MyAdapter(this@HomeActivity, item)
            recyclerView.adapter = myAdapter
        }
    }

    private suspend fun newsImages(): MutableList<ItemData?> {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            val response = retrofitClient.news("IBOVESPA", primeiroDiaDoMes(), "pt-br")
            val articles = response.body()?.articles
            val item: MutableList<ItemData?> = mutableListOf()

            if (articles == null) {
                item.add(ItemData("https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg", ""))
                item.add(ItemData("https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg", ""))
                return@async item
            }

            articles.forEach{ article -> item.add(ItemData(article.urlToImage, article.title ?: "")) }
            return@async item
        }
        return deferred.await()
    }

    private fun setData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitClient.ibovespa()

                if (response.isSuccessful) {
                    response.body()?.let { dadosResponse ->
                        val entries = mutableListOf<Entry>()
                        val labels = mutableListOf<String>()
                        val fechamentoValues = dadosResponse.dados.map { it.fechamento }

                        val minValue = fechamentoValues.minOrNull() ?: 0f
                        val maxValue = fechamentoValues.maxOrNull() ?: 0f

                        // Ajuste os limites do eixo Y para focar nas variações
                        lineChart.axisLeft.axisMinimum = minValue - 500f
                        lineChart.axisLeft.axisMaximum = maxValue + 500f

                        for ((index, dado) in dadosResponse.dados.withIndex()) {
                            val fechamento = dado.fechamento
                            entries.add(Entry(index.toFloat(), fechamento))
                            labels.add(dado.data)
                        }

                        withContext(Dispatchers.Main) {
                            val lineDataSet = LineDataSet(entries, "Valores")
                            lineDataSet.color = 0xFF00FF00.toInt()
                            lineDataSet.valueTextColor = 0xFFFFFFFF.toInt()
                            lineDataSet.lineWidth = 2f
                            lineDataSet.circleRadius = 2f
                            lineDataSet.setDrawCircles(true)
                            lineDataSet.setDrawValues(true)
                            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                            lineChart.axisLeft.textColor = 0xFFFFFFFF.toInt()
                            lineChart.xAxis.textColor = 0xFFFFFFFF.toInt()
                            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                            lineChart
                            lineChart.xAxis.setLabelCount(4, true)

                            val lineData = LineData(lineDataSet)
                            lineChart.data = lineData
                            lineChart.legend.textColor = 0xFFFFFFFF.toInt()
                            lineChart.invalidate()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setValor() {

        CoroutineScope(Dispatchers.Main).launch {
            val response = retrofitClient.ibovespa()
            Log.d("RESPOSTA", response.body().toString())
            val a = response.body()?.dados?.lastOrNull()?.fechamento
            textValue.text = "RS " + a.toString()

        }
    }

    private fun primeiroDiaDoMes(): String {
        val hoje = LocalDate.now()
        val primeiroDia = hoje.withDayOfMonth(1)  // Define o dia como o primeiro do mês
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Define o formato
        return primeiroDia.format(formato)
    }

}

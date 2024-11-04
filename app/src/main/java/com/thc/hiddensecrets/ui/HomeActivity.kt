package com.thc.hiddensecrets.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : AppCompatActivity() {

    lateinit var lineChart: LineChart
    lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    var xValues: List<String> = listOf()
    private var data: List<String> = listOf()
    private val retrofitClient by lazy {
        RetrofitClient(this).createService(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_navigator_view)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomTrovao = bottom.menu.findItem(R.id.navigation_dashboard)
        val bottomHome = bottom.menu.findItem(R.id.navigation_home)

        val imageView = findViewById<ImageView>(R.id.imageViewItem)

        // Defina o clique para mudar a imagem
        imageView.setOnClickListener {
            // Mudar a imagem para outra (por exemplo, outro drawable)
            setContentView(R.layout.activity_profile)
        }

        // navega para outra tela pelo trovão
        bottomTrovao.setOnMenuItemClickListener {
            val intent: Intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            // Ação para o item "Home"
            true
        }
        // Se você quiser definir outro comportamento para o item "Home"
        val homeMenuItem = bottom.menu.findItem(R.id.navigation_home)
        homeMenuItem.setOnMenuItemClickListener {
            // Ação para o item "Home"
            true
        }

        val rootView = findViewById<View>(android.R.id.content)
        rootView.setBackgroundColor(Color.parseColor("#171820"))
        lineChart = findViewById(R.id.chart)

        // config do grafico para ficar igual a do figma
        lineChart.setBackgroundColor(Color.TRANSPARENT) // Cor de fundo
        lineChart.axisLeft.setDrawGridLines(false)  // Desativa as linhas de grade no eixo Y
        lineChart.xAxis.setDrawGridLines(false)     // Desativa as linhas de grade no eixo X
        lineChart.xAxis.setDrawLabels(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.axisRight.isEnabled = false       // Desativa o eixo direito
        lineChart.description.isEnabled = false
        setData()

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Exemplo de URLs de imagens
        val imageUrls = listOf(
            "https://ichef.bbci.co.uk/news/1024/branded_portuguese/2df9/live/04b0f080-5318-11ef-b2d2-cdb23d5d7c5b.jpg",
            "https://f.i.uol.com.br/fotografia/2024/07/17/172124012966980a41457a1_1721240129_3x2_md.jpg",
            // Adicione mais URLs conforme necessário
        )

        val myAdapter = MyAdapter(this, imageUrls)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

    }

    private fun setData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitClient.ibovespa()

                if (response.isSuccessful) {
                    response.body()?.let { dadosResponse ->
                        val entries = mutableListOf<Entry>()
                        val months = mutableListOf<String>() // Para armazenar os rótulos dos meses

                        // Processando os dados do JSON
                        for (dado in dadosResponse.dados) {
                            // Converter data para um formato que o gráfico entenda
                            val splitDate = dado.data.split("-")
                            val month = splitDate[1].toFloat() // Meses
                            val fechamento = dado.fechamento // Fechamento

                            // Adiciona a entrada ao gráfico
                            entries.add(Entry(month, fechamento))
                            // Adiciona o mês correspondente à lista de meses
                            months.add(getMonthName(month.toInt())) // Adiciona o nome do mês
                        }

                        // Atualiza o gráfico na thread principal
                        withContext(Dispatchers.Main) {
                            val lineDataSet = LineDataSet(entries, "Valores")
                            lineDataSet.color = 0xFF00FF00.toInt() // Cor da linha
                            lineDataSet.valueTextColor = 0xFFFFFFFF.toInt() // Cor do texto
                            lineDataSet.lineWidth = 2f // Espessura da linha
                            lineDataSet.circleRadius = 2f // Raio dos círculos nos pontos
                            lineDataSet.setDrawCircles(true) // Desenhar círculos nos pontos
                            lineDataSet.setDrawValues(true) // Desenhar valores acima dos pontos

                            // Habilitar linhas cúbicas
                            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                            lineChart.axisLeft.textColor = 0xFFFFFFFF.toInt()
                            lineChart.xAxis.textColor = 0xFFFFFFFF.toInt()

                            // Define rótulos personalizados para o eixo X
                            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(months)

                            val lineData = LineData(lineDataSet)
                            lineChart.data = lineData
                            lineChart.legend.textColor = 0xFFFFFFFF.toInt()
                            lineChart.invalidate() // Atualiza o gráfico
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Trate erros aqui
            }
        }
    }

    // Função para obter o nome do mês
    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "Jan"
            2 -> "Fev"
            3 -> "Mar"
            4 -> "Abr"
            5 -> "Mai"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Ago"
            9 -> "Set"
            10 -> "Out"
            11 -> "Nov"
            12 -> "Dez"
            else -> ""
        }
    }



}
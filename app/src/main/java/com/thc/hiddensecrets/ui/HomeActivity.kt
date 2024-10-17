package com.thc.hiddensecrets.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R
import java.util.Arrays


class HomeActivity : AppCompatActivity() {

    lateinit var lineChart: LineChart
    lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    var xValues: List<String> = listOf()
    private var data: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_navigator_view)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomTrovao = bottom.menu.findItem(R.id.navigation_dashboard)

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
        recyclerView =
            findViewById(R.id.recyclerView) // Certifique-se de ter um RecyclerView no seu layout
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Exemplo de URLs de imagens
        val imageUrls = listOf(
            "https://files.tecnoblog.net/wp-content/uploads/2022/09/stable-diffusion-imagem.jpg",
            "https://cdn.pixabay.com/photo/2024/06/06/22/19/piece-8813495_640.png",
            // Adicione mais URLs conforme necessário
        )

        val myAdapter = MyAdapter(this, imageUrls)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

    }

    private fun setData() {
        val entries = mutableListOf<Entry>()

        // Adicione seus dados aqui (valores fictícios)
        val years = listOf(1990, 1994, 1998, 2002, 2006, 2010, 2014, 2018, 2022)
        val values = listOf(30f, 70f, 90f, 50f, 120f, 60f, 100f, 80f, 110f)

        for (i in years.indices) {
            entries.add(Entry(years[i].toFloat(), values[i]))
        }

        val lineDataSet = LineDataSet(entries, "Valores")
        lineDataSet.color = 0xFF00FF00.toInt() // Cor da linha
        lineDataSet.valueTextColor = 0xFFFFFFFF.toInt() // Cor do texto
        lineDataSet.lineWidth = 2f // Espessura da linha
        lineDataSet.circleRadius = 2f // Raio dos círculos nos pontos
        lineDataSet.setDrawCircles(true) // Desenhar círculos nos pontos
        lineDataSet.setDrawValues(true) // Desenhar valores acima dos pontos

        // Habilitar linhas cúbicas
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.invalidate() // Atualiza o gráfico
    }

}
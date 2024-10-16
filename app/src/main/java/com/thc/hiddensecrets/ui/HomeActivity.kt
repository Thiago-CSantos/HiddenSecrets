package com.thc.hiddensecrets.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    var xValues: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_navigator_view)
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

//
//
//        description.text = "Record"
//        description.setPosition(150f, 15f)
//        lineChart.description = description
//        lineChart.axisRight.setDrawLabels(true)
//
//        xValues = listOf("sajkdhasjk", "FDSFDSF", "FDGDFG")
//
//        val xAxis: XAxis = lineChart.xAxis
//        xAxis.textColor = Color.parseColor("#AAAAAA")
//        xAxis.setDrawLabels(true)
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
//        xAxis.setLabelCount(4)
//        xAxis.granularity = 1f
//
//        var yAxis = lineChart.axisLeft
//        yAxis.axisLineWidth = 2f
//        yAxis.textColor = Color.parseColor("#AAAAAA")
//        yAxis.axisLineColor = Color.parseColor("#00B74F")
//        yAxis.setLabelCount(10)
//
//        var e1: MutableList<Entry> = mutableListOf()
//        e1.add(Entry(0f, 10f))
//        e1.add(Entry(1f, 10f))
//        e1.add(Entry(2f, 15f))
//        e1.add(Entry(3f, 45f))
//
//        var dataSet1: LineDataSet = LineDataSet(e1, "Rótulo 1")
//        dataSet1.color = Color.parseColor("#00FF00")
//        dataSet1.valueTextColor = Color.parseColor("#FFFFFF") // Cor dos valores em branco
//        dataSet1.setDrawFilled(true)  // Habilita o preenchimento
//        dataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER // Habilitar linhas cúbicas
//
//
//        val lineData: LineData = LineData(dataSet1)
//
//        lineChart.data = lineData
//        lineChart.invalidate()
//
//        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        navBar.setBackgroundColor(Color.RED)
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
        lineDataSet.valueTextColor = 0xFF000000.toInt() // Cor do texto
        lineDataSet.lineWidth = 0.5f // Espessura da linha
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
package com.thc.hiddensecrets.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.fragmentos.HomeFragmento

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_navigator_view)

        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

//        mudarFragmento(HomeFragmento())

        val pointsData: List<Point> =
            listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))




    }

    private fun configurarGrafico(chartView: ChartView) {
        // Criação dos pontos de dados
        val pointsData: List<Point> = listOf(
            Point(0f, 40f),
            Point(1f, 90f),
            Point(2f, 0f),
            Point(3f, 60f),
            Point(4f, 10f)
        )

        // Configuração do eixo X
        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(Color.BLUE)
            .steps(pointsData.size - 1)
            .labelData { i -> i.toString() }
            .labelAndAxisLinePadding(15.dp)
            .build()

        // Configuração do eixo Y
        val steps = 5 // Defina o número de steps no eixo Y
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.RED)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                val yScale = 100 / steps
                (i * yScale).formatToSinglePrecision()
            }.build()

        // Atribuindo os dados aos eixos e ao gráfico
        chartView.setData(pointsData)
        chartView.setXAxisData(xAxisData)
        chartView.setYAxisData(yAxisData)

    private fun mudarFragmento(fragmento: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmento_container,fragmento).commit()
    }

}
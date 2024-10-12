package com.thc.hiddensecrets

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thc.hiddensecrets.ui.HomeActivity
import com.thc.hiddensecrets.ui.RegisterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun onClickEntrar(view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun onClickRegister(view: View) {
        // Criar um Intent para abrir a RegisterActivity
        val intent = Intent(this, RegisterActivity::class.java)

        // Iniciar a nova Activity
        startActivity(intent)
        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show()
    }

}
package com.thc.hiddensecrets

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thc.hiddensecrets.Service.ApiService
import com.thc.hiddensecrets.network.RetrofitClient
import com.thc.hiddensecrets.network.request.LoginRequest
import com.thc.hiddensecrets.ui.HomeActivity
import com.thc.hiddensecrets.ui.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var _email: EditText;
    private lateinit var _senha: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        _email = findViewById(R.id.editTextTextEmailAddress6)
        _senha = findViewById(R.id.editTextTextTelefone)

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
        val email: String = _email.text.toString().trim()
        val password: String = _senha.text.toString().trim()

        // Verifica se os campos estão vazios
        if (TextUtils.isEmpty(email)) {
            _email.error = "Por favor, preencha o e-mail."
            Toast.makeText(this, _email.error, Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            _senha.error = "Por favor, preencha a senha."
            Toast.makeText(this, _senha.error, Toast.LENGTH_SHORT).show()
            return
        }

        val retrofitClient = RetrofitClient().createService(ApiService::class.java)
        val loginRequest = LoginRequest(email, password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitClient.login(loginRequest)

                if (response.isSuccessful) {
                    // Navega para a HomeActivity no Main Dispatcher
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Erro durante a chamada de login", e)
                    Toast.makeText(this@MainActivity, "Erro na conexão. Tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun onClickRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
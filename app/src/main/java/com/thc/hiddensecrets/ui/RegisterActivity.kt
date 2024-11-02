package com.thc.hiddensecrets.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thc.hiddensecrets.R
import com.thc.hiddensecrets.Service.ApiService
import com.thc.hiddensecrets.network.RetrofitClient
import com.thc.hiddensecrets.network.request.CreateUserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var _nome: EditText
    private lateinit var _telefone: EditText
    private lateinit var _email: EditText
    private lateinit var _senha: EditText

    private val retrofitClient by lazy {
        RetrofitClient(this).createService(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        _nome = findViewById(R.id.editTextTextEmailAddress6)
        _telefone = findViewById(R.id.editTextTextTelefone)
        _email = findViewById(R.id.editTextTextEmailAddress2)
        _senha = findViewById(R.id.editTextTextPassword2)
    }

    fun onRegistrar(view: View) {
        val nome = _nome.text.toString().trim()
        val telefone = _telefone.text.toString().trim()
        val email = _email.text.toString().trim()
        val senha = _senha.text.toString().trim()

        if (!isInputValid(nome, telefone, email, senha)) return

        val user = CreateUserRequest(nome, telefone, email, senha)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitClient.createUser(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Cadastro Realizado", Toast.LENGTH_SHORT).show()
                        finish()  // Finaliza a activity após o cadastro bem-sucedido
                    } else {
                        Toast.makeText(this@RegisterActivity, "Falha no cadastro. Verifique seus dados.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Erro durante a chamada de registrar", e)
                    Toast.makeText(this@RegisterActivity, "Erro na conexão. Tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isInputValid(nome: String, telefone: String, email: String, senha: String): Boolean {
        if (TextUtils.isEmpty(nome)) {
            _nome.error = "Por favor, preencha o seu nome."
            showToast(_nome.error.toString())
            return false
        }

        if (TextUtils.isEmpty(telefone)) {
            _telefone.error = "Por favor, preencha o telefone."
            showToast(_telefone.error.toString())
            return false
        }

        if (TextUtils.isEmpty(email)) {
            _email.error = "Por favor, preencha o e-mail."
            showToast(_email.error.toString())
            return false
        }

        if (TextUtils.isEmpty(senha)) {
            _senha.error = "Por favor, preencha a senha."
            showToast(_senha.error.toString())
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClickVoltar(view: View) {
        finish() // Finaliza a Activity atual e volta para a anterior
    }
}

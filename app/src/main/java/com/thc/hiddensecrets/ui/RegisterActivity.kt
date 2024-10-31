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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        _nome = findViewById(R.id.editTextTextEmailAddress6)
        _telefone = findViewById(R.id.editTextTextTelefone)
        _email = findViewById(R.id.editTextTextEmailAddress2)
        _senha = findViewById(R.id.editTextTextPassword2)

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

    fun onRegistrar(view: View) {
        val nome = _nome.text.toString().trim()
        val telefone = _telefone.text.toString().trim()
        val email = _email.text.toString().trim()
        val senha = _senha.text.toString().trim()
        val user: CreateUserRequest = CreateUserRequest(nome, telefone, email, senha)

        // Verifica se os campos estão vazios
        if (TextUtils.isEmpty(nome)) {
            _email.error = "Por favor, preencha o seu nome."
            Toast.makeText(this, _email.error, Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(telefone)) {
            _email.error = "Por favor, preencha o telefone."
            Toast.makeText(this, _email.error, Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(email)) {
            _email.error = "Por favor, preencha o e-mail."
            Toast.makeText(this, _email.error, Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(senha)) {
            _senha.error = "Por favor, preencha a senha."
            Toast.makeText(this, _senha.error, Toast.LENGTH_SHORT).show()
            return
        }

        val retrofitClient = RetrofitClient().createService(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitClient.createUser(user)
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Cadastro Realizado", Toast.LENGTH_SHORT)
                        .show()
                    onDestroy()

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Falha no login. Verifique suas credenciais.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Erro durante a chamada de registrar", e)
                    Toast.makeText(this@RegisterActivity, "Erro na conexão. Tente novamente.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun onClickVoltar(view: View) {
        finish() // vai destruir activity atual e voltar para a anterior a ela
    }

}
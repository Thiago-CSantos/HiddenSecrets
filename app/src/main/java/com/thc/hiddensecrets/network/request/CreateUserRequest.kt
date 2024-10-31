package com.thc.hiddensecrets.network.request

data class CreateUserRequest(val nome: String, val telefone: String, val email: String, val senha: String)
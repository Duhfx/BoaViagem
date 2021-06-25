package com.example.boaviagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.boaviagem.database.BoaViagemDatabase
import com.example.boaviagem.database.UsuarioRepository

import com.example.boaviagem.model.Usuario
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class ActivityRegistrar : AppCompatActivity() {

    private lateinit var repository: UsuarioRepository
    private lateinit var editNome: Editable
    private lateinit var editEmail: Editable
    private lateinit var editSenha: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        editNome = findViewById<EditText>(R.id.ed_nome_registrar).text;
        editEmail = findViewById<EditText>(R.id.ed_email_registrar).text;
        editSenha = findViewById<EditText>(R.id.ed_senha_registrar).text;

        repository =
            UsuarioRepository(BoaViagemDatabase.getDatabase(this).UsuarioDao())
    }

    fun onRegistrar(view: View) {
        val dadosUsuario = Usuario(editNome.toString(), editEmail.toString(), editSenha.toString())

        GlobalScope.launch {
            val id = repository.adicionaUsuario(dadosUsuario)
            if (id != 0L)
              LimpaCampos()
      }
    }

    private fun LimpaCampos() {
        editNome.clear()
        editEmail.clear()
        editSenha.clear()
    }
}
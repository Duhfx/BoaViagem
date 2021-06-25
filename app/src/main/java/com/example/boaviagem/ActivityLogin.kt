package com.example.boaviagem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.boaviagem.database.BoaViagemDatabase
import com.example.boaviagem.database.UsuarioRepository
import com.example.boaviagem.model.Usuario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ActivityLogin : AppCompatActivity() {
    private lateinit var repository: UsuarioRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        repository =
            UsuarioRepository(BoaViagemDatabase.getDatabase(this).UsuarioDao())
    }

    fun onRegistrar(view: View) {
        startActivity(Intent(this, ActivityRegistrar::class.java))
    }

    fun onEntrar(view: View) {
        val dadosUsuario = ValidaUsuario();

        if (dadosUsuario == null || dadosUsuario.id == 0)
            Toast.makeText(this, "Errou", Toast.LENGTH_LONG).show()
        else
            startActivity(Intent(this, ActivityHome::class.java))
    }

    private fun ValidaUsuario(): Usuario {
        val editEmail = findViewById<EditText>(R.id.ed_usuario_login).text;
        val editSenha = findViewById<EditText>(R.id.ed_senha_login).text;

        return runBlocking {
            repository.usuarioExiste(editEmail.toString(), editSenha.toString())
        }
    }
}
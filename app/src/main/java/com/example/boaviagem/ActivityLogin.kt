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
        val idUsuario = validaUsuario();

        if(idUsuario != 0) {
            val intent = Intent(this, ActivityHome::class.java)
            intent.putExtra(EXTRA_ID_USUARIO, idUsuario)

            startActivity(intent)
        }
    }

    private fun validaUsuario(): Int {
        val editEmail = findViewById<EditText>(R.id.ed_usuario_login).text;
        val editSenha = findViewById<EditText>(R.id.ed_senha_login).text;

        if (editEmail.toString().isEmpty() || editSenha.toString().isEmpty()) {
            Toast.makeText(this, "Insira seu usuário e senha", Toast.LENGTH_SHORT).show()
            return 0;
        }

        val dadosUsuario = runBlocking {
            repository.usuarioExiste(editEmail.toString(), editSenha.toString())
        }

        if (dadosUsuario == null || dadosUsuario.id == 0) {
            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
            return 0;
            return 0;
        }

        return dadosUsuario.id;
    }
}
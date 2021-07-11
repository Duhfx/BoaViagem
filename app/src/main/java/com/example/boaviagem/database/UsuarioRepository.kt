package com.example.boaviagem.database

import com.example.boaviagem.dao.UsuarioDao
import com.example.boaviagem.model.Usuario
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun adicionaUsuario(usuario: Usuario): Long {
        return withContext(IO) {
            usuarioDao.Insert(usuario)
        }
    }

    suspend fun usuarioExiste(email: String, senha: String): Usuario? {
        return withContext(IO) {
            usuarioDao.FindByEmailPassword(email, senha)
        };
    }
}
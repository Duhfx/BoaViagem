package com.example.boaviagem.database

import com.example.boaviagem.dao.ViagemDao
import com.example.boaviagem.model.Viagem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViagemRepository(private val viagemDao: ViagemDao) {

    suspend fun adicionarNovaViagem(viagem: Viagem): Long {
        return withContext(Dispatchers.IO) {
            viagemDao.Insert(viagem)
        }
    }

    suspend fun buscaViagemUsuario(id: Int): List<Viagem> {
        return withContext(Dispatchers.IO) {
            viagemDao.BuscaViagens();
        }
    }
}
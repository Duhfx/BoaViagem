package com.example.boaviagem.database

import com.example.boaviagem.dao.ViagemDao
import com.example.boaviagem.model.Viagem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViagemRepository(private val viagemDao: ViagemDao) {

    suspend fun adicionarNovaViagem(viagem: Viagem): Long {
        return withContext(Dispatchers.IO) {
            viagemDao.insert(viagem)
        }
    }

    suspend fun buscaViagemUsuario(idUsuario: Int): List<Viagem> {
        return withContext(Dispatchers.IO) {
            viagemDao.buscaViagens(idUsuario);
        }
    }
}
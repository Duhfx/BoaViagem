package com.example.boaviagem.database

import com.example.boaviagem.dao.GastoDao
import com.example.boaviagem.model.Gasto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GastoRepository(private val gastoDao: GastoDao) {

    suspend fun adicionaGasto(gasto: Gasto): Long {
        return withContext(Dispatchers.IO) {
            gastoDao.Insert(gasto)
        }
    }

    suspend fun buscaGastoPorViagem(idViagem: Int): List<Gasto> {
        return withContext(Dispatchers.IO) {
            gastoDao.buscaGastosPorViagem(idViagem)
        }
    }

    suspend fun deletaGasto(gasto: Gasto){
        return withContext(Dispatchers.IO) {
            gastoDao.delete(gasto);
        }
    }
}
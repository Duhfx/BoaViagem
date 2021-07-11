package com.example.boaviagem.dao

import androidx.room.*
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.Usuario

@Dao
interface GastoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(gasto: Gasto): Long

    @Query("SELECT * FROM Gasto WHERE idViagem = :idViagem")
    fun buscaGastosPorViagem(idViagem: Int): List<Gasto>

    @Delete
    fun delete(gasto: Gasto)
}
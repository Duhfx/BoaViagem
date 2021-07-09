package com.example.boaviagem.dao

import androidx.room.*
import com.example.boaviagem.model.Viagem

@Dao
interface ViagemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Viagem): Long

    @Query("SELECT * FROM Viagem WHERE idUsuario = :idUsuario")
    fun buscaViagens(idUsuario: Int) : List<Viagem>

    @Delete
    fun delete(viagem: Viagem)
}
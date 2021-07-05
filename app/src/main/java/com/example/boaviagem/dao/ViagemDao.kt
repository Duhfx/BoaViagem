package com.example.boaviagem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boaviagem.model.Viagem

@Dao
interface ViagemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(user: Viagem): Long

    @Query("SELECT * FROM Viagem")
    fun BuscaViagens() : List<Viagem>

}
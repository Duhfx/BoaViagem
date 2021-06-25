package com.example.boaviagem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boaviagem.model.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(user: Usuario): Long

    @Query("SELECT * FROM Usuario WHERE EMAIL = :emailUsuario AND SENHA = :senhaUsuario")
    fun FindByEmailPassword(emailUsuario: String, senhaUsuario: String): Usuario
}
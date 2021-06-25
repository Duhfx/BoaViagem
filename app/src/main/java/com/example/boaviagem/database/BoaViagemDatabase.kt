package com.example.boaviagem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boaviagem.dao.UsuarioDao
import com.example.boaviagem.model.Usuario

@Database(entities = arrayOf(Usuario::class), version = 1)
abstract class BoaViagemDatabase : RoomDatabase() {
    abstract  fun UsuarioDao() : UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: BoaViagemDatabase? = null

        fun getDatabase(context: Context): BoaViagemDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BoaViagemDatabase::class.java,
                    "boaviagem_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}
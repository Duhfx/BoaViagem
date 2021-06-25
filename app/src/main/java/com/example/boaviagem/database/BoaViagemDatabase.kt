package com.example.boaviagem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.boaviagem.dao.UsuarioDao
import com.example.boaviagem.dao.ViagemDao
import com.example.boaviagem.model.Usuario
import com.example.boaviagem.model.Viagem

@Database(entities = arrayOf(Usuario::class, Viagem::class), version = 2)
@TypeConverters(Converters::class)
abstract class BoaViagemDatabase : RoomDatabase() {
    abstract  fun UsuarioDao() : UsuarioDao
    abstract  fun ViagemDao() : ViagemDao

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
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}
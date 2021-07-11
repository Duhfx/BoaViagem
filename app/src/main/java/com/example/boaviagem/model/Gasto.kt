package com.example.boaviagem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Gasto(val descricao: String,
                 val local: String,
                 val data: Date,
                 val valor: Double,
                 val tipoGasto: TipoGasto) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var idViagem: Int = 0
}

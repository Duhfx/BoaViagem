package com.example.boaviagem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Viagem(val destino: String, val dataChegada: Date,
                  val dataPartida: Date, val orcamento: Double,
                  val tipoViagem: TipoViagem, val idUsuario: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

package com.example.boaviagem.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.boaviagem.R
import java.util.*

@Entity
data class Viagem(val destino: String, val dataChegada: Date,
                  val dataPartida: Date, val orcamento: Double,
                  val tipoViagem: TipoViagem, val idUsuario: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getIDResourceTipo(): Int {
        if (tipoViagem == TipoViagem.NEGOCIO)
            return R.drawable.ic_viagem_negocio
        else if (tipoViagem == TipoViagem.LAZER)
            return R.drawable.ic_viagem_lazer

        return 0
    }
}

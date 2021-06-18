package com.example.boaviagem.model

import java.util.*

data class Viagem(val destino: String, val dataChegada: Date, val dataPartida: Date, val orcamento: Double, val tipoViagem: TipoViagem) {

}

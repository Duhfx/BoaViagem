package com.example.boaviagem.model

import java.util.*

data class Gasto(val descricao: String, val local: String, val data: Date, val valor: Double, val tipoGasto: TipoGasto)

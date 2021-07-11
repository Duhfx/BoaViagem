package com.example.boaviagem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.model.Gasto
import kotlinx.coroutines.runBlocking

class GastoAdapter(private val listaGasto: List<Gasto>) : RecyclerView.Adapter<GastoAdapter.GastoViewHolder>() {

    class GastoViewHolder(itemGasto: View) : RecyclerView.ViewHolder(itemGasto) {
        val edtDescricao: TextView = itemGasto.findViewById(R.id.ed_descricao_gasto_item)
        val edtValor: TextView = itemGasto.findViewById(R.id.ed_valor_gasto_item)
        val imgDelete: ImageView = itemGasto.findViewById(R.id.img_deletar_gasto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val itemViagem = LayoutInflater.from(parent.context).inflate(R.layout.gasto_list,
            parent, false)

        return GastoViewHolder(itemViagem)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = listaGasto[position]

        holder.edtDescricao.text = gasto.descricao
        holder.edtValor.text    = gasto.valor.toString()

        val activityHome = holder.itemView.context as ActivityHome

        holder.imgDelete.setOnClickListener {
            runBlocking {
                activityHome.getGastoRepository().deletaGasto(gasto)
            }

            activityHome.atualizaRecyclerGasto()
        }
    }

    override fun getItemCount() = listaGasto.size
}
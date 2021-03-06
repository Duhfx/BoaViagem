package com.example.boaviagem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.model.Viagem
import kotlinx.coroutines.runBlocking

class ViagemAdapter(private val listaViagem: List<Viagem>) : RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    class ViagemViewHolder(itemViagem: View) : RecyclerView.ViewHolder(itemViagem) {
        val imgView: ImageView   = itemViagem.findViewById(R.id.viagem_imagem_list)
        val imgDelete: ImageView = itemViagem.findViewById(R.id.viagem_deletarViagem)
        val txtDestino: TextView = itemViagem.findViewById(R.id.viagem_destino_list)
        val txtTipo: TextView    = itemViagem.findViewById(R.id.viagem_tipo_list)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {
        val itemViagem = LayoutInflater.from(parent.context).inflate(R.layout.viagem_list,
            parent, false)

        return ViagemViewHolder(itemViagem)
    }

    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {
        val viagem = listaViagem[position]

        holder.imgView.setImageResource(viagem.getIDResourceTipo())
        holder.txtDestino.text = viagem.destino
        holder.txtTipo.text    = viagem.tipoViagem.toString()

        val activityHome = holder.itemView.context as ActivityHome

        holder.imgDelete.setOnClickListener {
            runBlocking {
                activityHome.getViagemRepository().deletaViagem(viagem)
            }

            activityHome.atualizaRecyclerViagem()
        }

        holder.itemView.setOnClickListener {
            activityHome.onViagemSelecionada(viagem.id)
        }
    }

    override fun getItemCount() = listaViagem.size
}
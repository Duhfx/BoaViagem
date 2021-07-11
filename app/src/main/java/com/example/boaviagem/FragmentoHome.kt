package com.example.boaviagem

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.model.TipoViagem
import com.example.boaviagem.model.Viagem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentoHome : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val activityHome = activity as ActivityHome

        view.findViewById<FloatingActionButton>(R.id.add_viagem).setOnClickListener {
            activityHome.selecionaFragmentNovaViagem();
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter       = ViagemAdapter(activityHome.getViagensItemUsuario())
        recyclerView.layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        return view;
    }

    fun atualizaAdapter(listaViagem: List<Viagem>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)

        if (recyclerView != null) {
            recyclerView.adapter = ViagemAdapter(listaViagem)
        }
    }

}
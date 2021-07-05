package com.example.boaviagem

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.boaviagem.database.BoaViagemDatabase
import com.example.boaviagem.database.ViagemRepository
import com.example.boaviagem.model.TipoViagem
import com.example.boaviagem.model.Viagem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FragmentoNovaViagem : Fragment() {
    lateinit var activityHome: ActivityHome

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_nova_viagem, container, false)
        val editDataChegada = view?.findViewById<EditText>(R.id.ed_data_chegada_viagem)
        val editDataPartida = view?.findViewById<EditText>(R.id.ed_data_partida_viagem)

        activityHome = activity as ActivityHome

        populaSpinner(view)

        view?.findViewById<Button>(R.id.btn_nova_viagem)?.setOnClickListener { onNovaViagem(view) }

        editDataChegada?.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    editDataChegada.setText("${dayOfMonth}/${month}/${year}")
                }

                datePickerDialog.show()
            }
        }

        editDataPartida?.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    editDataPartida.setText("${dayOfMonth}/${month}/${year}")
                }

                datePickerDialog.show()
            }
        }

        return view
    }

    private fun populaSpinner(view: View) {
        val spinner = view?.findViewById<Spinner>(R.id.select_tipo)

        if (context != null) {
            ArrayAdapter.createFromResource(
                context!!,
                R.array.TiposViagem,
                android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                if (spinner != null) {
                    spinner.adapter = adapter
                    spinner.setSelection(0)
                }
            }
        }
    }

    private fun getTipoSelecionado(): TipoViagem {
        val spinner = view?.findViewById<Spinner>(R.id.select_tipo)
        val itemSelecionado = spinner?.selectedItem as String

        if (itemSelecionado == "Lazer")
            return TipoViagem.LAZER
        else if (itemSelecionado == "Negócio")
            return TipoViagem.NEGOCIO;

        return TipoViagem.LAZER;
    }

    private fun onNovaViagem(view: View) {
        val idUsuario = activityHome.getIDUsuarioLogado()
        val destino = view.findViewById<EditText>(R.id.ed_destino_viagem).text;
        val dataPartidaTxt = view.findViewById<EditText>(R.id.ed_data_partida_viagem).text;
        val dataChegadaTxt = view.findViewById<EditText>(R.id.ed_data_chegada_viagem).text;

        val dataPartida = SimpleDateFormat("dd/MM/yyyy").parse(dataPartidaTxt.toString())!!
        val dataChegada = SimpleDateFormat("dd/MM/yyyy").parse(dataChegadaTxt.toString())!!

        val dadosViagem = Viagem(destino.toString(), dataChegada, dataPartida, 0.0,  getTipoSelecionado(), idUsuario);

        GlobalScope.launch {
            val id = activityHome.getViagemRepository().adicionarNovaViagem(dadosViagem)
        }
    }

    fun inicializaEditData(editData: EditText) {
        editData?.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    editData.setText("${dayOfMonth}/${month}/${year}")
                }

                datePickerDialog.show()
            }
        }
    }
}
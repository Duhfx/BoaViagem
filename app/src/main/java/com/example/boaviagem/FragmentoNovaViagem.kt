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
import com.example.boaviagem.model.Viagem

class FragmentoNovaViagem : Fragment() {

    private lateinit var repository: ViagemRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_nova_viagem, container, false)
        val editDataChegada = view?.findViewById<EditText>(R.id.ed_data_chegada_viagem)
        val editDataPartida = view?.findViewById<EditText>(R.id.ed_data_partida_viagem)

        val viagemDao = context?.let { BoaViagemDatabase.getDatabase(it).ViagemDao() }

        repository =
            viagemDao?.let { ViagemRepository(it) }!!

        populaSpinner(view)

        //view?.findViewById<Button>(R.id.btn_nova_viagem)?.setOnClickListener { onNovaViagem(view) }

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

    fun onNovaViagem(view: View) {

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
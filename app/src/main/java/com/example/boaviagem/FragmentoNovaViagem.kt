package com.example.boaviagem

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

class FragmentoNovaViagem : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_nova_viagem, container, false)
        val editDataChegada = view?.findViewById<EditText>(R.id.ed_data_chegada_viagem)

        editDataChegada?.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    editDataChegada.setText(year.toString())
                }

                datePickerDialog.show()
            }
        }

        return view
    }


}
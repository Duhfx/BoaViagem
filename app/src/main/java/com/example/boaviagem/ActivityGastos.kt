package com.example.boaviagem

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.database.BoaViagemDatabase
import com.example.boaviagem.database.GastoRepository
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.TipoGasto
import java.text.SimpleDateFormat
import java.util.*

class ActivityGastos : AppCompatActivity() {
    private lateinit var repositoryGasto: GastoRepository
    private lateinit var edtDataGasto: EditText
    private val dateFormat = "dd/MM/yyyy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        edtDataGasto = findViewById(R.id.ed_data_gasto)
        repositoryGasto = GastoRepository(BoaViagemDatabase.getDatabase(this).GastoDao())

        edtDataGasto.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)

            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                edtDataGasto.setText("${dayOfMonth}/${month}/${year}")
            }

            datePickerDialog.show()
        }
    }

    fun onNovoGasto(view: View) {
        val edtDescricao: EditText = findViewById(R.id.ed_descricao_gasto)
        val edtLocal: EditText = findViewById(R.id.ed_local_gasto)
        val edtValor: EditText = findViewById(R.id.ed_valor_gasto)

        val dataGasto = SimpleDateFormat(dateFormat).parse(edtDataGasto.text.toString())!!

        val dadosGasto = Gasto(edtDescricao.text.toString(), edtLocal.text.toString(), dataGasto, edtValor.text.toString().toDouble(), getTipoGastoSelecionado())

        val output = Intent()
        output.putExtra(EXTRA_NOVA_VIAGEM, dadosGasto)

        setResult(RESULT_OK, output)
        finish()
    }

    private fun getTipoGastoSelecionado(): TipoGasto {
        return when {
            findViewById<RadioButton>(R.id.rd_gasto_combustivel).isChecked -> TipoGasto.ALIMENTACAO
            findViewById<RadioButton>(R.id.rd_gasto_alimentacao).isChecked -> TipoGasto.ALIMENTACAO
            findViewById<RadioButton>(R.id.rd_gasto_hospedagem).isChecked -> TipoGasto.HOSPEDAGEM
            else -> TipoGasto.ALIMENTACAO
        }

    }

    private fun getIDViagemGasto(): Int {
        return intent.extras?.getInt(EXTRA_ID_VIAGEM)!!
    }
}
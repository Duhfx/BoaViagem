package com.example.boaviagem

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.TipoViagem
import com.example.boaviagem.model.Viagem
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat

const val EXTRA_NOVA_VIAGEM = "OBJ_VIAGEM"
const val COD_NOVO_GASTO = 1
const val EXTRA_ID_VIAGEM = "ID_VIAGEM"


class FragmentoNovaViagem : Fragment() {
    lateinit var activityHome: ActivityHome
    private lateinit var edtDestinoViagem: EditText
    private lateinit var edtDataPartida: EditText
    private lateinit var edtDataChegada: EditText
    private lateinit var edtOrcamento: EditText
    private lateinit var btnNovaViagem: Button
    private lateinit var btnAdicionarGasto: Button
    private lateinit var recyclerGasto: RecyclerView

    var idViagemSelecionada = 0

    private val dateFormat = "dd/MM/yyyy"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_nova_viagem, container, false)

        recyclerGasto    = view.findViewById(R.id.recycler_gasto)
        edtDestinoViagem = view.findViewById(R.id.ed_destino_viagem);
        edtDataPartida   = view.findViewById(R.id.ed_data_partida_viagem)
        edtDataChegada   = view.findViewById(R.id.ed_data_chegada_viagem)
        edtOrcamento     = view.findViewById(R.id.ed_orcamento_viagem)

        btnNovaViagem = view.findViewById(R.id.btn_nova_viagem)
        btnAdicionarGasto = view.findViewById(R.id.btn_adicionarGasto)

        activityHome = activity as ActivityHome

        btnNovaViagem.setOnClickListener     { onNovaViagem(view) }
        btnAdicionarGasto.setOnClickListener { onAdicionarGasto(view) }

        edtDataChegada.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    edtDataChegada.setText("${dayOfMonth}/${month}/${year}")
                }

                datePickerDialog.show()
            }
        }

        edtDataPartida.setOnClickListener {
            val datePickerDialog = activity?.let { itActivity -> DatePickerDialog(itActivity) }

            if (datePickerDialog != null) {
                datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                    edtDataPartida.setText("${dayOfMonth}/${month}/${year}")
                }

                datePickerDialog.show()
            }
        }

        defineRecyclerGastos()
        limpaCampos()
        onViagemExistente();

        return view
    }

    fun setDadosViagem(viagem: Viagem) {
        setTipoSelecionado(viagem.tipoViagem)

        edtDestinoViagem.setText(viagem.destino)
        edtDataPartida.setText(SimpleDateFormat(dateFormat).format(viagem.dataPartida))
        edtDataChegada.setText(SimpleDateFormat(dateFormat).format(viagem.dataChegada))
        edtOrcamento.setText(viagem.orcamento.toString())

        idViagemSelecionada = viagem.id

        onViagemExistente()
        atualizaRecyclerGasto()
    }

    fun atualizaRecyclerGasto() {
        recyclerGasto.adapter       = GastoAdapter(activityHome.getGastosViagem(idViagemSelecionada))
    }

    private fun defineRecyclerGastos() {
        recyclerGasto.layoutManager = LinearLayoutManager(context)
        recyclerGasto.setHasFixedSize(true)
    }

    private fun getTipoSelecionado(): TipoViagem {
        val btnLazer = view?.findViewById<RadioButton>(R.id.radio_lazer)

        return if (btnLazer != null && btnLazer.isChecked)
            TipoViagem.LAZER;
        else
            TipoViagem.NEGOCIO;
    }

    private fun setTipoSelecionado(tipoViagem: TipoViagem) {
        if (tipoViagem == TipoViagem.LAZER)
            view?.findViewById<RadioGroup>(R.id.radioGroup)?.check(R.id.radio_lazer)
        else if (tipoViagem == TipoViagem.NEGOCIO)
            view?.findViewById<RadioGroup>(R.id.radioGroup)?.check(R.id.radio_negocio)
    }

    private fun onNovaViagem(view: View) {
        if (!validaCampos())
            return;

        val idUsuario   = activityHome.getIDUsuarioLogado()
        val dataPartida = SimpleDateFormat(dateFormat).parse(edtDataPartida.text.toString())!!
        val dataChegada = SimpleDateFormat(dateFormat).parse(edtDataChegada.text.toString())!!

        val dadosNovaViagem = Viagem(edtDestinoViagem.text.toString(), dataChegada, dataPartida, edtOrcamento.text.toString().toDouble(),  getTipoSelecionado(), idUsuario);

        dadosNovaViagem.id = idViagemSelecionada

        val idViagem = runBlocking {
            activityHome.getViagemRepository().adicionarNovaViagem(dadosNovaViagem)
        }

        if (idViagem != 0L) {

            if (idViagemSelecionada != 0) {
                Toast.makeText(activityHome, "Viagem atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                activityHome.selecionaFragmentoHome()
            }
            else {
                limpaCampos()
                Toast.makeText(activityHome, "Viagem cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpaCampos() {
        edtDestinoViagem.text.clear()
        edtDataPartida.text.clear()
        edtDataChegada.text.clear()
        edtOrcamento.text.clear()

        setTipoSelecionado(TipoViagem.LAZER)

        idViagemSelecionada = 0;
    }

    private fun validaCampos(): Boolean {
        if (edtDestinoViagem.text.toString().isEmpty()){
            Toast.makeText(context, "Informe o destino", Toast.LENGTH_SHORT).show()
            return false;
        }

        if (edtDataPartida.text.toString().isEmpty()){
            Toast.makeText(context, "Informe a data de partida", Toast.LENGTH_SHORT).show()
            return false;
        }

        if (edtDataChegada.text.toString().isEmpty()){
            Toast.makeText(context, "Informe a data de chegada", Toast.LENGTH_SHORT).show()
            return false;
        }

        return true;
    }

    private fun onAdicionarGasto(view: View) {
        startActivityForResult(Intent(activityHome, ActivityGastos::class.java).putExtra(EXTRA_ID_VIAGEM, idViagemSelecionada), COD_NOVO_GASTO)
    }

    private fun onViagemExistente() {
        val viagemExiste = idViagemSelecionada != 0

        btnAdicionarGasto.visibility = if (viagemExiste) VISIBLE else INVISIBLE

        if (viagemExiste)
            btnNovaViagem.setText(R.string.atualizar_viagem)
        else
            btnNovaViagem.setText(R.string.adicionar_viagem)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null || idViagemSelecionada == 0)
            return;

        if (resultCode == Activity.RESULT_OK && requestCode == COD_NOVO_GASTO) {
            val dadosGastoAdicionado = data.getSerializableExtra(EXTRA_NOVA_VIAGEM) as Gasto

            dadosGastoAdicionado.idViagem = idViagemSelecionada

            runBlocking {
                activityHome.getGastoRepository().adicionaGasto(dadosGastoAdicionado)
            }

            atualizaRecyclerGasto()
        }
    }
}
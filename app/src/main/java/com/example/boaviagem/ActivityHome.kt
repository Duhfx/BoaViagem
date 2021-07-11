package com.example.boaviagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.boaviagem.database.BoaViagemDatabase
import com.example.boaviagem.database.GastoRepository
import com.example.boaviagem.database.ViagemRepository
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.Viagem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val EXTRA_ID_USUARIO = "ID_USUARIO"

class ActivityHome : AppCompatActivity() {

    private lateinit var repository: ViagemRepository
    private lateinit var repositoryGasto: GastoRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        repository = ViagemRepository(BoaViagemDatabase.getDatabase(this).ViagemDao())
        repositoryGasto = GastoRepository(BoaViagemDatabase.getDatabase(this).GastoDao())

        createFragment(FragmentoHome())

        val bottomNav = findViewById<BottomNavigationView>(R.id.navigationBar)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragmento_home -> createFragment(FragmentoHome())
                R.id.fragmento_nova_viagem -> createFragment(FragmentoNovaViagem())
                R.id.fragmento_ajuda -> createFragment(FragmentoAjuda())
                else -> false
            }
        }
    }

    private fun createFragment(fragmento: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framePrincipal, fragmento)
            .addToBackStack(null)
            .commit()

        return true
    }

    fun selecionaFragmentNovaViagem() {
        createFragment(FragmentoNovaViagem());

        findViewById<BottomNavigationView>(R.id.navigationBar).selectedItemId = R.id.fragmento_nova_viagem
    }

    fun selecionaFragmentoHome() {
        createFragment(FragmentoHome());

        findViewById<BottomNavigationView>(R.id.navigationBar).selectedItemId = R.id.fragmento_home
    }

    fun getViagensItemUsuario(): List<Viagem> {
        val idUsuario = getIDUsuarioLogado()
        var listaViagem: List<Viagem>

        runBlocking {
            listaViagem = repository.buscaViagemUsuario(idUsuario)
        }

        return listaViagem;
    }

    fun getGastosViagem(idViagem: Int): List<Gasto> {
        var  listaGastos: List<Gasto>

        runBlocking {
            listaGastos = repositoryGasto.buscaGastoPorViagem(idViagem)
        }

        return listaGastos;
    }

    fun atualizaRecyclerViagem() {
        val fragHome = supportFragmentManager.fragments.last() as FragmentoHome

        fragHome.atualizaAdapter(getViagensItemUsuario())
    }

    fun atualizaRecyclerGasto() {
        val fragViagem = supportFragmentManager.fragments.last() as FragmentoNovaViagem

        fragViagem.atualizaRecyclerGasto()
    }

    fun getIDUsuarioLogado(): Int {
        return intent.extras?.getInt(EXTRA_ID_USUARIO)!!
    }

    fun getViagemRepository(): ViagemRepository {
        return repository
    }

    fun getGastoRepository(): GastoRepository {
        return repositoryGasto
    }

    fun onViagemSelecionada(idViagem: Int) {
        selecionaFragmentNovaViagem()

        supportFragmentManager.executePendingTransactions()

        val dadosViagem = runBlocking {
            repository.buscaViagem(idViagem)
        }

        (supportFragmentManager.fragments.last() as FragmentoNovaViagem).setDadosViagem(dadosViagem)
    }

}
package com.example.boaviagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val EXTRA_ID_USUARIO = "ID_USUARIO"

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        val bottomNav = findViewById<BottomNavigationView>(R.id.navigationBar)

        bottomNav.selectedItemId = R.id.fragmento_nova_viagem
    }

    fun getIDUsuarioLogado(): Int? {
        return intent.extras?.getInt(EXTRA_ID_USUARIO)
    }

}
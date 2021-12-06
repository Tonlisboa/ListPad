package com.example.listpad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.listpad.viewmodel.ListPadFormViewModel
import com.example.listpad.R
import com.example.listpad.service.constants.ListaConstants
import kotlinx.android.synthetic.main.activity_listpad_form.*

class ListaFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ListPadFormViewModel
    private var mListId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listpad_form)

        mViewModel = ViewModelProvider(this).get(ListPadFormViewModel::class.java)

        // Eventos
        setListeners()

        // Cria observadores
        observe()

        // Carrega dados do usuário, caso haja
        loadData()

         // Default
        radio_urgente.isChecked = true
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_salvar) {

            val nome = edit_nome.text.toString()
            val urgente = radio_urgente.isChecked
            mViewModel.save(mListId, nome, urgente)
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mListId = bundle.getInt(ListaConstants.LISTATID)
            mViewModel.load(mListId)
        }
    }

    private fun observe() {
        mViewModel.salvaLista.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.lista.observe(this, Observer {
            edit_nome.setText(it.nome)
            if (it.urgente) {
                radio_urgente.isChecked = true
            } else {
                radio_nao_urgente.isChecked = true
            }
        })
    }

    private fun setListeners() {
        button_salvar.setOnClickListener(this)
    }
}

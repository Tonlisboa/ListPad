package com.example.listpad.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listpad.R
import com.example.listpad.service.constants.ListaConstants
import com.example.listpad.view.adapter.ListaAdapter
import com.example.listpad.view.listener.ListaListener
import com.example.listpad.viewmodel.ListPadViewModel

class NaoUrgenteFragment : Fragment() {

    private lateinit var mViewModel: ListPadViewModel
    private val mAdapter: ListaAdapter = ListaAdapter()
    private lateinit var mListener: ListaListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {

        mViewModel = ViewModelProvider(this).get(ListPadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_naourgente, container, false)

        // Elemento de interface - RecyclerView
        // Não é possível deixar o Kotlin fazer o mapeamento, pois a fragment ainda não está totalmente criada
        // Assim, precisamos buscar o elemento através de findViewById
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_nao_urgentes)

        // Atribui um layout que diz como a RecyclerView se comporta
        recycler.layoutManager = LinearLayoutManager(context)

        // Defini um adapater - Faz a ligação da RecyclerView com a listagem de itens
        recycler.adapter = mAdapter

        // Cria os observadores
        observe()

        // Cria comportamento quando item for clicado
        mListener = object : ListaListener {
            override fun onClick(id: Int) {
                // Intenção
                val intent = Intent(context, ListaFormActivity::class.java)

                // "Pacote" de valores que serão passados na navegação
                val bundle = Bundle()
                bundle.putInt(ListaConstants.LISTATID, id)

                // Atribui o pacote a Intent
                intent.putExtras(bundle)

                // Inicializa Activity com dados
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(ListaConstants.FILTER.NAOURGENTE)
            }
        }

        mAdapter.attachListener(mListener)

        // Retorna a Fragment criada
        return root
    }

    /**
     * Ciclo de vida - onResume
     */
    override fun onResume() {
        super.onResume()
        mViewModel.load(ListaConstants.FILTER.NAOURGENTE)
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        mViewModel.listaList.observe(viewLifecycleOwner, Observer {
            mAdapter.atualizaLista(it)
        })
    }
}

package com.example.listpad.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listpad.R
import com.example.listpad.service.model.ListaModel
import com.example.listpad.view.listener.ListaListener
import com.example.listpad.view.viewholder.ListaViewHolder

class ListaAdapter : RecyclerView.Adapter<ListaViewHolder>() {

    // Lista de itens
    private var mList: List<ListaModel> = arrayListOf()
    private lateinit var mListener: ListaListener

    /**
     * Faz a criação do layout da linha
     * Faz a criação de várias linhas que vão mostrar cada linha da lista
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_listapad, parent, false)
        return ListaViewHolder(item, mListener)
    }

    /**
     * Qual o tamanho da RecyclerView
     */
    override fun getItemCount(): Int {
        return mList.count()
    }

    /**
     * Para cada linha, este método é chamado
     * É responsável por atribuir os valores de cada item para uma linha específica
     */
    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    /**
     * Atualização da lista de itens
     */
    fun atualizaLista(list: List<ListaModel>) {
        mList = list
        notifyDataSetChanged()
    }

    /**
     * Eventos na listagem
     */
    fun attachListener(listener: ListaListener) {
        mListener = listener
    }

}
package com.example.listpad.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listpad.R
import com.example.listpad.service.model.ListaModel
import com.example.listpad.view.listener.ListaListener

class ListaViewHolder(itemView: View, private val listener: ListaListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(lista: ListaModel) {

        // Obtém os elementos de interface
        val textName = itemView.findViewById<TextView>(R.id.text_nome)

        // Atribui valores
        textName.text = lista.nome

        // Atribui eventos
        textName.setOnClickListener {
            listener.onClick(lista.id)
        }

        // Atribui eventos
        textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_listpad)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which ->
                    listener.onDelete(lista.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }

    }
}
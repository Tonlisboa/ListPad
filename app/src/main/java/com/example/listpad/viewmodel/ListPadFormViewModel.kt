package com.example.listpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listpad.service.model.ListaModel
import com.example.listpad.service.repository.ListaRepository

class ListPadFormViewModel(application: Application) : AndroidViewModel(application) {

    // Contexto e acesso a dados
    private val mContext = application.applicationContext
    private val mListaRepository: ListaRepository = ListaRepository.getInstance(mContext)

    private var mSalvaLista = MutableLiveData<Boolean>()
    val salvaLista: LiveData<Boolean> = mSalvaLista

    private var mList = MutableLiveData<ListaModel>()
    val lista: LiveData<ListaModel> = mList

    /**
     * Salva lista
     * */
    fun save(id: Int, nome: String, urgente: Boolean) {
        val lista = ListaModel(id, nome, urgente)

        if (id == 0) {
            mSalvaLista.value = mListaRepository.save(lista)
        } else {
            mSalvaLista.value = mListaRepository.update(lista)
        }
    }

    /**
     * Carrega lista
     * */
    fun load(id: Int) {
        mList.value = mListaRepository.get(id)
    }

}
package com.example.listpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listpad.service.constants.ListaConstants
import com.example.listpad.service.model.ListaModel
import com.example.listpad.service.repository.ListaRepository

class ListPadViewModel(application: Application) : AndroidViewModel(application) {

    private val mListRepository = ListaRepository.getInstance(application.applicationContext)

    private val mtList = MutableLiveData<List<ListaModel>>()
    val listaList: LiveData<List<ListaModel>> = mtList

    fun load(filter: Int) {

        if (filter == ListaConstants.FILTER.TODOS) {
            mtList.value = mListRepository.getAll()
        } else if (filter == ListaConstants.FILTER.URGENTE) {
            mtList.value = mListRepository.getUrgente()
        } else {
            mtList.value = mListRepository.getNaoUgente()
        }
    }

    fun delete(id: Int) {
        mListRepository.delete(id)
    }

}
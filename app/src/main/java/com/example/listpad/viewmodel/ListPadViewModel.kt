package com.example.listpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listpad.service.constants.ListaConstants
import com.example.listpad.service.model.ListaModel
import com.example.listpad.service.repository.ListaRepository

class ListPadViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = ListaRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<ListaModel>>()
    val listaList: LiveData<List<ListaModel>> = mGuestList

    fun load(filter: Int) {

        if (filter == ListaConstants.FILTER.TODOS) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (filter == ListaConstants.FILTER.URGENTE) {
            mGuestList.value = mGuestRepository.getUrgente()
        } else {
            mGuestList.value = mGuestRepository.getNaoUgente()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }

}
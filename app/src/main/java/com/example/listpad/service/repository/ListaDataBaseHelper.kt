package com.example.listpad.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.listpad.service.constants.DataBaseConstants

class ListaDataBaseHelper(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    /**
     * Método executado somente uma vez quando o acesso ao banco de dados é feito pela primeira vez
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_LISTPAD)
    }

    /**
     * Método executado quando a versão do DATABASE_VERSION é alterada
     * Dessa maneira, a aplicação sabe que o banco de dados foi alterado e é necessário rodar o script de update
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val VERSION = 1
        private const val NAME = "ListPad.db"

        private const val CREATE_TABLE_LISTPAD =
            ("create table " + DataBaseConstants.LISTA.TABLE_NAME + " ("
                    + DataBaseConstants.LISTA.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.LISTA.COLUMNS.NOME + " text, "
                    + DataBaseConstants.LISTA.COLUMNS.URGENTE + " integer);")
    }

}
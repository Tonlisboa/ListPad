package com.example.listpad.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.listpad.service.constants.DataBaseConstants
import com.example.listpad.service.model.ListaModel
import java.lang.Exception
import java.util.ArrayList

class ListaRepository private constructor(context: Context) {

    // Acesso ao banco de dados
    private var mListaDataBaseHelper: ListaDataBaseHelper = ListaDataBaseHelper(context)

    /**
     * Singleton
     */
    companion object {
        private lateinit var repository: ListaRepository

        fun getInstance(context: Context): ListaRepository {
            if (!::repository.isInitialized) {
                repository = ListaRepository(context)
            }
            return repository
        }
    }

    /**
     * Carrega a lista
     */
    fun get(id: Int): ListaModel? {

        var lista: ListaModel? = null
        return try {
            val db = mListaDataBaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(
                DataBaseConstants.LISTA.COLUMNS.NOME,
                DataBaseConstants.LISTA.COLUMNS.URGENTE
            )

            // Filtro
            val selection = DataBaseConstants.LISTA.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.LISTA.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            // Verifica se existem dados no cursor
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val nome = cursor.getString(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.NOME))
                val urgente = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.URGENTE)) == 1)

                lista = ListaModel(id, nome, urgente)
            }

            cursor?.close()
            lista
        } catch (e: Exception) {
            lista
        }
    }

    /**
     * Insere convidado
     */
    fun save(lista: ListaModel): Boolean {
        return try {

            // writableDatabase - Para fazer escrita de dados
            val db = mListaDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.LISTA.COLUMNS.NOME, lista.nome)
            contentValues.put(DataBaseConstants.LISTA.COLUMNS.URGENTE, lista.urgente)
            db.insert(DataBaseConstants.LISTA.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Faz a listagem de todos os dados
     */
    fun getAll(): List<ListaModel> {
        val list: MutableList<ListaModel> = ArrayList()
        return try {
            val db = mListaDataBaseHelper.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(
                DataBaseConstants.LISTA.COLUMNS.ID,
                DataBaseConstants.LISTA.COLUMNS.NOME,
                DataBaseConstants.LISTA.COLUMNS.URGENTE
            )

            // Linha única
            // Cursor cursor = db.rawQuery("select * from ListPad", null);

            // Faz a seleção
            val cursor = db.query(
                DataBaseConstants.LISTA.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.ID))
                    val nome = cursor.getString(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.NOME))
                    val urgente = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.URGENTE)) == 0)

                    val lista = ListaModel(id, nome, urgente)
                    list.add(lista)
                }

                // Como verificar se um valor é nulo
                cursor.isNull(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.URGENTE)))
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    /**
     * Faz a listagem de todos os lista de itens urgentes
     */
    fun getUrgente(): List<ListaModel> {
        val list: MutableList<ListaModel> = ArrayList()
        return try {
            val db = mListaDataBaseHelper.readableDatabase

            val cursor = db.rawQuery("SELECT id, nome, urgente FROM ListPad WHERE urgente = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.ID))
                    val nome = cursor.getString(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.NOME))
                    val urgente = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.URGENTE)) == 1)

                    val lista = ListaModel(id, nome, urgente)
                    list.add(lista)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    /**
     * Faz a listagem de todos não urgentes
     */
    fun getNaoUgente(): List<ListaModel> {
        val list: MutableList<ListaModel> = ArrayList()
        return try {
            val db = mListaDataBaseHelper.readableDatabase

            val cursor = db.rawQuery("SELECT id, nome, urgente FROM ListPad WHERE urgente = 2", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.ID))
                    val nome = cursor.getString(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.NOME))
                    val urgente = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.LISTA.COLUMNS.URGENTE)) == 2)

                    val lista = ListaModel(id, nome, urgente)
                    list.add(lista)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    /**
     * Atualiza a lista
     */
    fun update(lista: ListaModel): Boolean {
        return try {
            val db = mListaDataBaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.LISTA.COLUMNS.NOME, lista.nome)
            contentValues.put(DataBaseConstants.LISTA.COLUMNS.URGENTE, lista.urgente)

            // Critério de seleção
            val selection = DataBaseConstants.LISTA.COLUMNS.ID + " = ?"
            val args = arrayOf(lista.id.toString())

            db.update(DataBaseConstants.LISTA.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Remove da lista
     */
    fun delete(id: Int): Boolean {
        return try {
            val db = mListaDataBaseHelper.writableDatabase
            val selection = DataBaseConstants.LISTA.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.LISTA.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

}
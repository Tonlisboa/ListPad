package com.example.listpad.service.constants

/**
 * Todas as constantes utilizadas no banco de dados
 * Tabelas, Colunas
 */
class DataBaseConstants private constructor() {

    /**
     * Tabelas disponíveis no banco de dados com suas colunas
     */
    object LISTA {
        const val TABLE_NAME = "ListPad"

        object COLUMNS {
            const val ID = "id"
            const val NOME = "nome"
            const val URGENTE = "urgente"
        }
    }
}
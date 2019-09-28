package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context
import android.content.SharedPreferences

class ConfiguracaoDatabase(context: Context){
    companion object{
        val NOME_ARQUIVO = "config_database"
        val MODO_ARQUIVO = Context.MODE_PRIVATE
        val TAG_CONFIGURACAO = "database"
    }

    val sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO,
            MODO_ARQUIVO)
    }

    fun setConfigDatabase(usaSqlite: Boolean) {
        val spEditor: SharedPreferences.Editor = sharedPreferences.edit()

        spEditor.putBoolean(TAG_CONFIGURACAO, usaSqlite)
        spEditor.commit()
    }

    fun getConfigDatabase(): Boolean {
        return sharedPreferences.getBoolean(TAG_CONFIGURACAO, false)
    }
}
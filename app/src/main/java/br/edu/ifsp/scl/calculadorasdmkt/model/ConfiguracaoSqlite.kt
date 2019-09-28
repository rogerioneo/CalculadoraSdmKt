package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class ConfiguracaoSqlite(context: Context): ConfiguracaoDao {
    companion object Constantes {
        val NOME_BD = "configuracoes"
        val MODO_BD = Context.MODE_PRIVATE
        val NOME_TABELA = "configuracao"
        val ATRIBUTO_ID = "id"
        val ATRIBUTO_LEIAUTE_AVANCADO = "leiauteAvancado"
        val ATRIBUTO_SEPARADOR = "separador"
        val ID_PADRAO = 0
        //Bool <-> Int porque Sqlite não tem Booleano
        val TRUE = 1
        val FALSE = 0

        // Executando uma vez, concatenação de Strings não influenciará desempenho
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${NOME_TABELA} (" +
                "${ATRIBUTO_ID} INTEGER, " +
                "${ATRIBUTO_LEIAUTE_AVANCADO} INTEGER, " +
                "${ATRIBUTO_SEPARADOR} TEXT);"
        val QUERY_TUPLE = "SELECT * FROM ${NOME_TABELA} WHERE id = ${ID_PADRAO}"
    }

    // Referência para BD Sqlite
    val sqliteBd: SQLiteDatabase
    init {
        // Criando a tabela se a mesma não existe
        sqliteBd = context.openOrCreateDatabase(NOME_BD, MODO_BD, null)

        // Criando a tabela se a mesma não existe
        try {
            sqliteBd.execSQL(CREATE_TABLE)
        }
        catch (e: SQLException){
            e.printStackTrace()
        }
    }

    override fun createOrUpdateConfiguracao(configuracao: Configuracao) {
        val leiauteAvancadoInt = if (configuracao.leiauteAvancado) TRUE else FALSE
        val resultadoCursor = sqliteBd.rawQuery(QUERY_TUPLE, arrayOf())

        // String que vai guardar qrery de update ou insert
        val query: String

        // Posiciona o cursor no inicio, é falso se o Cursor estiver vazio
        if (resultadoCursor.moveToFirst()){
            // Update
            query = "UPDATE ${NOME_TABELA} SET " +
                    "${ATRIBUTO_LEIAUTE_AVANCADO} = ${leiauteAvancadoInt}, " +
                    "${ATRIBUTO_SEPARADOR} = '${configuracao.separador}' " +
                    "WHERE ${ATRIBUTO_ID} = ${ID_PADRAO};"
        } else {
            // Insert
            query = "INSERT INTO ${NOME_TABELA} VALUES (" +
                    "${ID_PADRAO}, ${leiauteAvancadoInt}, " +
                    "'${configuracao.separador}');"
        }

        try {
            sqliteBd.execSQL(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun readConfiguracao(): Configuracao {
        val resultadoCursor = sqliteBd.rawQuery(QUERY_TUPLE, arrayOf())

        return if (resultadoCursor.moveToFirst()) {
            val indiceLeiauteAvancado = resultadoCursor.getColumnIndex(ATRIBUTO_LEIAUTE_AVANCADO)
            val indiceSeparador = resultadoCursor.getColumnIndex(ATRIBUTO_SEPARADOR)
            val leiauteAvancadoCursor = resultadoCursor.getInt(indiceLeiauteAvancado)
            val separadorCursor = resultadoCursor.getString(indiceSeparador)
            val leiauteAvancado = if (leiauteAvancadoCursor == TRUE) true else false
            val separador = if (separadorCursor.equals(Separador.PONTO.name)) Separador.PONTO else Separador.VIRGULA
            Configuracao(leiauteAvancado, separador)
        } else
            Configuracao()
    }
}
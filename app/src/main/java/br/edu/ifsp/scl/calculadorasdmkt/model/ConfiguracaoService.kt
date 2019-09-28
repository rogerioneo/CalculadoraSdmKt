package br.edu.ifsp.scl.calculadorasdmkt.model

import android.content.Context

class ConfiguracaoService(context: Context) {
    var configuracaoDatabase: ConfiguracaoDatabase
    var configuracaoSharedPreferenceDao: ConfiguracaoDao
    var configuracaoSqliteDao: ConfiguracaoDao
    init{
        // Inicializando conforme a fonte de dados utilizada
        configuracaoDatabase = ConfiguracaoDatabase(context)
        configuracaoSharedPreferenceDao = ConfiguracaoSharedPreferences(context)
        configuracaoSqliteDao = ConfiguracaoSqlite(context)
    }
    fun setConfiguracao(usarSqlite: Boolean, configuracao: Configuracao) {
        /* Qualquer tratamento necessário aos dados antes de salvá-los
        na fonte de dados escolhida deve ser feita no Service.
        As classes que implementam o DAO devem esconder as peculiaridades
        para acesso a cada fonte de dados diferente e executar apenas as funções
        de CRUD.*/
        configuracaoDatabase.setConfigDatabase(usarSqlite)
        if(usarSqlite)
            configuracaoSqliteDao.createOrUpdateConfiguracao(configuracao)
        else
            configuracaoSharedPreferenceDao.createOrUpdateConfiguracao(configuracao)
    }
    fun getUsaSqlite() = configuracaoDatabase.getConfigDatabase()

    fun getConfiguracao(): Configuracao {
        return if (getUsaSqlite()) configuracaoSqliteDao.readConfiguracao()
                          else configuracaoSharedPreferenceDao.readConfiguracao()
    }
}
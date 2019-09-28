package br.edu.ifsp.scl.calculadorasdmkt.controller

import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.ConfiguracaoService
import br.edu.ifsp.scl.calculadorasdmkt.view.ConfiguracaoActivity

class ConfiguracaoController(val view: ConfiguracaoActivity) {
    // Model
    val model: ConfiguracaoService
    init{
        model = ConfiguracaoService(view.applicationContext)
    }

    fun salvaConfiguracao(usarSqlite: Boolean, configuracao: Configuracao) {
        model.setConfiguracao(usarSqlite, configuracao)
        view.atualizaView(usarSqlite, configuracao)
    }

    fun buscaConfiguracao() {
        val configuracao = model.getConfiguracao()
        view.atualizaView(model.getUsaSqlite(), configuracao)
    }
}
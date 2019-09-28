package br.edu.ifsp.scl.calculadorasdmkt.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.controller.ConfiguracaoController
import br.edu.ifsp.scl.calculadorasdmkt.model.Configuracao
import br.edu.ifsp.scl.calculadorasdmkt.model.Separador
import kotlinx.android.synthetic.main.activity_configuracao.*
import kotlinx.android.synthetic.main.toolbar.*

class ConfiguracaoActivity: AppCompatActivity() {
    object Constantes {
        // Chave de retorno para a MainActivity
        val CONFIGURACAO = "CONFIGURACAO"
    }

    // Referência para Controller
    lateinit var configuracaoController: ConfiguracaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)

        // Toolbar
        toolbar.title = "Configuração"
        setSupportActionBar(toolbar)

        // Chama controller e atualizar view
        configuracaoController = ConfiguracaoController(this)
        configuracaoController.buscaConfiguracao()
    }

    // Função chamada pelo Controller depois de acessar o Model
    fun atualizaView(usarSqlite: Boolean, configuracao: Configuracao) {
        // Ajusta o leiaute conforme a configuração
        armazenamentoSpn.setSelection(if (usarSqlite) 1 else 0 )
        leiauteSpn.setSelection( if (configuracao.leiauteAvancado) 1 else 0 )
        separadorRg.check(
            if (configuracao.separador == Separador.PONTO)
                R.id.pontoRb
            else
                R.id.virgulaRb
        )

        // SETAR RESULTADO PARA MAIN ACTIVTY
        setResult(AppCompatActivity.RESULT_OK, Intent().
            putExtra(Constantes.CONFIGURACAO, configuracao))
    }

    fun onClickSalvaConfiguracao(@Suppress("UNUSED_PARAMETER") v: View) {
        // Pega os dados da tela
        val usarSqlite = armazenamentoSpn.selectedItemPosition == 1
        val leiauteAvancado = leiauteSpn.selectedItemPosition == 1
        val separador = if (pontoRb.isChecked) Separador.PONTO else Separador.VIRGULA

        // Criar um objeto Configuracao
        val novaConfiguracao: Configuracao = Configuracao(leiauteAvancado, separador)

        // Chamar o Controller para salvar
        configuracaoController.salvaConfiguracao(usarSqlite, novaConfiguracao)

        if (usarSqlite)
            Toast.makeText(this, "Configuração salva no Sqlite!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Configuração salva no SharedPreference!", Toast.LENGTH_SHORT).show()

        this.onBackPressed()
    }
}
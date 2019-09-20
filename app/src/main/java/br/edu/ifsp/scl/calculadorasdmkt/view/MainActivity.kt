package br.edu.ifsp.scl.calculadorasdmkt.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import br.edu.ifsp.scl.calculadorasdmkt.R
import br.edu.ifsp.scl.calculadorasdmkt.utils.Calculadora
import br.edu.ifsp.scl.calculadorasdmkt.utils.Operador

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var concatenaLcd: Boolean = true

    //Altere o aplicativo para incluir operações de raiz quadrada, porcentagem,
    // limpeza de operação recente (CE) e limpeza de operação total (C).
    //Após isso, faça o upload do repositório do seu projeto no ambiente virtual da disciplina.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seteBt.setOnClickListener(this)
        oitoBt.setOnClickListener(this)
        noveBt.setOnClickListener(this)
        adicaoBt.setOnClickListener(this)

        umBt.setOnClickListener {
            if (!concatenaLcd) {
                lcdTv.text = ""
            }
            lcdTv.append((it as Button).text.toString())
            concatenaLcd = true
        }

        doisBt.setOnClickListener { it ->
            if (!concatenaLcd) {
                lcdTv.text = ""
            }
            lcdTv.append((it as Button).text.toString())
            concatenaLcd = true
        }

        tresBt.setOnClickListener { x: View ->
            if (!concatenaLcd) {
                lcdTv.text = ""
            }
            lcdTv.append((x as Button).text.toString())
            concatenaLcd = true
        }

        quatroBt.setOnClickListener { onClickBtNum(it) }
        cindoBt.setOnClickListener(::onClickBtNum)
        seisBt.setOnClickListener { onClickBtNum(it) }

        multiplicacaoBt.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                lcdTv.text = Calculadora.calcula(
                    lcdTv.text.toString().toFloat(),
                    Operador.MULTIPLICACAO
                ).toString()
                concatenaLcd = false
            }
        })

        zeroBt.setOnClickListener(::onClickZeroPontoResultadoDivisao)
        pontoBt.setOnClickListener() { ::onClickZeroPontoResultadoDivisao }
        resultadoBt.setOnClickListener(::onClickZeroPontoResultadoDivisao)
        val f: (View) -> Unit = ::onClickZeroPontoResultadoDivisao
        divisaoBt.setOnClickListener(f)
    }

    override fun onClick(p0: View?) {
        if (p0 == seteBt || p0 == oitoBt || p0 == noveBt){
            if (!concatenaLcd) {
              lcdTv.text = ""
            }
            lcdTv.append((p0 as Button).text.toString())
            concatenaLcd = true
        }
        else {
            if (p0 == adicaoBt) {
                lcdTv.text = Calculadora.calcula(
                    lcdTv.text.toString().toFloat(),
                    Operador.ADICAO
                ).toString()
                concatenaLcd = false
            }
        }
    }

    fun onClickBtNum(p0: View?) {
        if (!concatenaLcd) {
            lcdTv.text = ""
        }
        lcdTv.append((p0 as Button).text.toString())
        concatenaLcd = true
    }

    fun onClickBtSub(p0: View?) {
        lcdTv.text = Calculadora.calcula(
            lcdTv.text.toString().toFloat(),
            Operador.SUBTRACAO
        ).toString()
        concatenaLcd = false
    }

    fun operacao(op: Operador){
        lcdTv.text = Calculadora.calcula(
            lcdTv.text.toString().toFloat(),
            op
        ).toString()
        concatenaLcd = false
    }

    fun onClickZeroPontoResultadoDivisao(view: View?) {
        when (view){
            zeroBt -> {
                // Limpa LCD se último clicado foi um operador
                if (!concatenaLcd) {
                    lcdTv.text = ""
                }
                lcdTv.append((view as Button).text.toString())
                concatenaLcd = true
            }
            pontoBt -> {
                if (!lcdTv.text.toString().contains(".")){
                    if (!concatenaLcd) {
                        lcdTv.text = "0"
                    }
                    lcdTv.append(".")
                    concatenaLcd = true
                }
            }
            resultadoBt -> {
                operacao(Operador.RESULTADO)
            }
            divisaoBt -> {
                operacao(Operador.ADICAO)
            }
        }
    }

}

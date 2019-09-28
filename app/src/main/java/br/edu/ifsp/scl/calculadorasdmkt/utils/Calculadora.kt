package br.edu.ifsp.scl.calculadorasdmkt.utils

/* Classe de enumeração para constantes de operadores */
enum class Operador {
    C, CE, RESULTADO, PORCENTO, ADICAO, SUBTRACAO, MULTIPLICACAO, DIVISAO
}

/* Singleton que calcula operações aritméticas básicas */
object Calculadora {
    // primeiro operando
    var operando: Float = 0.0f

    // operador que será aplicado entre primeiro e segundo operando
    var operador: Operador =
        Operador.RESULTADO

    /* calcula um valor de retorno com base no operando e operador já existentes, novo valor
     e atualiza valor de operando e operador */
    fun calcula(valor: Float, operador: Operador): Float {
        if (operador == Operador.CE) return 0f
        if (operador == Operador.PORCENTO) return (valor / 100)
        when (Calculadora.operador) {
            Operador.RESULTADO -> operando = valor
            Operador.ADICAO -> operando += valor
            Operador.SUBTRACAO -> operando -= valor
            Operador.MULTIPLICACAO -> operando *= valor
            Operador.DIVISAO -> operando /= valor
        }
        Calculadora.operador = operador
        if (Calculadora.operador == Operador.C) operando = 0f
        return operando
    }
}
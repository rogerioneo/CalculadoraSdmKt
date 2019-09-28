package br.edu.ifsp.scl.calculadorasdmkt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class Separador(val text: String) {
    VIRGULA(","), PONTO(".")
}

@Parcelize
data class Configuracao(var leiauteAvancado: Boolean = false,
                        var separador: Separador = Separador.PONTO): Parcelable
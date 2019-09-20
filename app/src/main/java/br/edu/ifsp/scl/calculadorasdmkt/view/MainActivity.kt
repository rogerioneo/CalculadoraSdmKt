package br.edu.ifsp.scl.calculadorasdmkt.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.calculadorasdmkt.R
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Toolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        // Fragment padrão
        supportFragmentManager.beginTransaction()
            .replace(R.id.calculadoraFl, CalculadoraBasicaFragment())
            .commit()
    }

}

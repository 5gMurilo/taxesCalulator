package com.example.calculadoradejuros

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var taxJuros:TextInputEditText
    private lateinit var montante:TextInputEditText
    private lateinit var tempoAplicacao:TextInputEditText
    private lateinit var btnCalc:Button
    private lateinit var titleResultado:TextView
    private lateinit var textResultado:TextView
    private lateinit var textJuro:TextView
    private lateinit var titleJuro:TextView
    private lateinit var radioComposto:RadioButton
    private lateinit var radioSimples:RadioButton

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taxJuros = findViewById(R.id.taxaJuros)
        montante = findViewById(R.id.granaInicial)
        tempoAplicacao = findViewById(R.id.tempoAplicacao)
        textResultado = findViewById(R.id.resultadoJuros)
        btnCalc = findViewById(R.id.btnCalcular)
        radioComposto = findViewById(R.id.radioBtn1)
        radioSimples = findViewById(R.id.radioBtn2)
        textJuro = findViewById(R.id.jurosTotal)
        titleJuro = findViewById(R.id.titlejuros)
        titleResultado = findViewById(R.id.titleResultado)

        var tax:Double
        var money:Double
        var time:Double

        btnCalc.setOnClickListener {

            val taxaDeJuros: String = taxJuros.text.toString()
            val dindin: String = montante.text.toString()
            val tempoApp: String = tempoAplicacao.text.toString()
            val df = DecimalFormat("#######.##")

            df.roundingMode = RoundingMode.CEILING

            //verifica qual o radio button selecionado
            if (radioComposto.isChecked) {
                // radio button de juros compostos
                tax = taxaDeJuros.toDouble()
                money = dindin.toDouble()
                time = tempoApp.toDouble()

                tax /= 100
                tax += 1

                var calcComposto = juros(time, money, tax, ::calcularComposto)
                titleResultado.text = "Seu dinheiro ao final desse período será equivalente a:"
                textResultado.text = "R$ ${df.format(calcComposto)}"
                calcComposto -= money
                titleJuro.text = "Os juros serão equivalentes a:"
                textJuro.text = "R$ ${df.format(calcComposto)}"

            } else if (radioSimples.isChecked) {
                // radio button de juros simples
                tax = taxaDeJuros.toDouble()
                money = dindin.toDouble()
                time = tempoApp.toDouble()

                tax /= 100

                var calcSimples = juros(time, money, tax, ::calcularSimples)
                titleResultado.text = "Seu dinheiro ao final desse período será equivalente a:"
                calcSimples += money
                textResultado.text = "R$ ${df.format(calcSimples)}"
                calcSimples -= money
                titleJuro.text = "Os juros serão equivalentes a:"
                textJuro.text = "R$ ${df.format(calcSimples)}"

            }

        }
    }

    //single line functions

    // formula de juros simples -> montante * tempo * taxa
    private fun calcularSimples(t: Double, m: Double, i: Double) = m * t * i
    // formula de juros compostos -> montante * (1 + taxa) ^ tempo
    private fun calcularComposto(t: Double, m: Double, i: Double) = m * i.pow(t)

    //high-order function
    //função de ordem superior
    private fun juros(
        t: Double,
        m: Double,
        i: Double,
        operation: (Double, Double, Double) -> Double
    ): Double {

        return operation(t, m, i)
    }


}


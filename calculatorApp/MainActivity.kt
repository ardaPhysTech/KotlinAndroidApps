package com.phystech.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


private const val OPERATION ="Operation"
private const val OPERAND1 = "OPERAND1"

class MainActivity : AppCompatActivity() {
    private val result by lazy<TextView>(LazyThreadSafetyMode.NONE){findViewById(R.id.result)}
    private val newNumber by lazy<EditText>(LazyThreadSafetyMode.NONE) {findViewById(R.id.newNumber) }
    private val operation by lazy<TextView>(LazyThreadSafetyMode.NONE) {findViewById(R.id.operation)  }
    private var operand1 : Double? = null
    private var operand2 : Double = 0.0
    private var pendingOperation = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button0 : Button = findViewById(R.id.button0)
        val button1 : Button = findViewById(R.id.button1)
        val button2 : Button = findViewById(R.id.button2)
        val button3 : Button = findViewById(R.id.button3)
        val button4 : Button = findViewById(R.id.button4)
        val button5 : Button = findViewById(R.id.button5)
        val button6 : Button = findViewById(R.id.button6)
        val button7 : Button = findViewById(R.id.button7)
        val button8 : Button = findViewById(R.id.button8)
        val button9 : Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        val listener = View.OnClickListener{v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val buttonMinus: Button = findViewById(R.id.buttonMinus)
        val buttonSum: Button = findViewById(R.id.buttonSum)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val buttonDivide: Button = findViewById(R.id.buttonDivide)

        val operandListener =  View.OnClickListener{v ->
            val op = (v as Button).text.toString()
            val value = newNumber.text.toString()

            if (newNumber.text.isNotEmpty()){
                performOperation(op, value)
            }
            pendingOperation = op
            operation.text = pendingOperation
        }

        buttonMinus.setOnClickListener(operandListener)
        buttonDivide.setOnClickListener(operandListener)
        buttonSum.setOnClickListener(operandListener)
        buttonMultiply.setOnClickListener(operandListener)
        buttonEquals.setOnClickListener(operandListener)

    }
    private fun performOperation(op: String, value: String){
        try{
            if(operand1 == null){
                operand1 = value.toDouble()
            }else{
                operand2 = value.toDouble()
                if(pendingOperation == "="){
                    pendingOperation = op
                }
                when(pendingOperation){
                    "=" -> operand1 = operand2
                    "+" -> operand1 = operand1!! + operand2
                    "-" -> operand1 = operand1!! - operand2
                    "*" -> operand1 = operand1!! * operand2
                    "/" -> if(operand2 == 0.0){
                        operand1 = Double.NaN
                    }else{
                        operand1 = operand1!! / operand2
                    }
                }
            }
            result.setText(operand1.toString())
            newNumber.text.clear()
        }catch(e : Exception){
            result.setText("NaN")
            newNumber.text.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(OPERATION, operation.text.toString())
        outState.putDouble(OPERAND1, operand1!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = savedInstanceState.getDouble(OPERAND1)
        operation.text =  savedInstanceState.getString(OPERATION)
        pendingOperation = operation.text.toString()


    }

}

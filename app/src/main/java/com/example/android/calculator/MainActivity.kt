package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            if (binding.result.hasFocus()) {
                binding.result.text.append(b.text)
            } else if (binding.newNumber.hasFocus()) {
                binding.newNumber.text.append(b.text)
            }
        }

        val operatorListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            if (binding.operation.hasFocus()) {
                binding.operation.setText(op)
            }
        }

        setNumberButtonListeners(listener)

        setOperatorButtonListeners(operatorListener)

        binding.buttonNeg.setOnClickListener {
            if (binding.result.hasFocus()) {
                negativeButtonCode(binding.result)
            } else if (binding.newNumber.hasFocus()) {
                negativeButtonCode(binding.newNumber)
            }
        }

        try {
            binding.buttonEquals.setOnClickListener {
                val firstNumber = binding.result.text.toString().toDouble()
                val secondNumber = binding.newNumber.text.toString().toDouble()
                val operator = binding.operation.text.toString()
                calculateResult(firstNumber, secondNumber, operator)
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(
                this@MainActivity, "Enter a valid number or operator!", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun negativeButtonCode(numberField: EditText) {
        if (numberField.text.isEmpty()) {
            numberField.setText("-")
        } else {
            try {
                var number = numberField.text.toString().toDouble()
                number *= -1.0
                numberField.setText(number.toString())
            } catch (e: NumberFormatException) {
                numberField.setText("")
            }
        }
    }

    private fun setOperatorButtonListeners(operatorListener: View.OnClickListener) {
        binding.buttonPlus.setOnClickListener(operatorListener)
        binding.buttonMultiply.setOnClickListener(operatorListener)
        binding.buttonMinus.setOnClickListener(operatorListener)
        binding.buttonDivide.setOnClickListener(operatorListener)
    }

    private fun setNumberButtonListeners(listener: View.OnClickListener) {
        binding.button0.setOnClickListener(listener)
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
        binding.buttonDot.setOnClickListener(listener)
    }

    private fun calculateResult(firstNumber: Double, secondNumber: Double, operator: String) {
        val result: Double
        Log.d("MainActivity", "$firstNumber $operator $secondNumber")
        when (operator) {
            "+" -> result = firstNumber + secondNumber
            "-" -> result = firstNumber - secondNumber
            "*" -> result = firstNumber * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    Toast.makeText(
                        this@MainActivity, "Division by zero is not possible", Toast.LENGTH_SHORT
                    ).show()
                    binding.result.setText("")
                    binding.newNumber.setText("")
                    binding.operation.setText("")
                    return
                } else {
                    result = firstNumber / secondNumber
                }
            }
            else -> {
                binding.operation.setText("")
                Toast.makeText(
                    this@MainActivity, "Please enter a valid operator!", Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
        binding.result.setText(result.toString())
        binding.newNumber.setText("")
        binding.operation.setText("")
    }
}
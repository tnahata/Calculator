package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.android.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // sets the content of the layout for the main activity
        setContentView(binding.root)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            if (binding.result.hasFocus()) {
                binding.result.text.append(b.text)
            } else {
                binding.newNumber.text.append(b.text)
            }
        }

        val operatorListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            binding.operation.setText(op)
        }

        binding.button0.setOnClickListener(listener) // add all to functions
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

        binding.buttonPlus.setOnClickListener(operatorListener) // add all to functions
        binding.buttonMultiply.setOnClickListener(operatorListener)
        binding.buttonMinus.setOnClickListener(operatorListener)
        binding.buttonDivide.setOnClickListener(operatorListener)

        binding.buttonNeg.setOnClickListener { // TODO improve this duplicated code
            if (binding.result.hasFocus()) {
                if (binding.result.text.isEmpty()) {
                    binding.result.setText("-")
                } else {
                    try {
                        var number = binding.result.text.toString().toDouble()
                        number *= -1.0
                        binding.result.setText(number.toString())
                    } catch (e: NumberFormatException) {
                        binding.result.setText("")
                    }
                }
            } else {
                if (binding.newNumber.text.isEmpty()) {
                    binding.result.setText("-")
                } else {
                    try {
                        var number = binding.newNumber.text.toString().toDouble()
                        number *= -1.0
                        binding.newNumber.setText(number.toString())
                    } catch (e: NumberFormatException) {
                        binding.newNumber.setText("")
                    }
                }
            }
        }

        try {
            binding.buttonEquals.setOnClickListener {
                val firstNumber = binding.result.text.toString().toDouble()
                //Log.d("MainActivity", "$firstNumber")
                val secondNumber = binding.newNumber.text.toString().toDouble()
                //Log.d("MainActivity", "$secondNumber")
                val operator = binding.operation.text.toString()
                //Log.d("MainActivity", operator)
                calculateResult(firstNumber, secondNumber, operator)
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(
                this@MainActivity, "Enter a valid number or operator", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.message?.let { Log.d("MainActivity", it) }
        }
    }

    private fun calculateResult(firstNumber: Double = 0.0, secondNumber: Double = 0.0, operator: String = "") {
        var result = 0.0
        Log.d("MainActivity", "$firstNumber $operator $secondNumber")
        when(operator) {
            "+" -> result = firstNumber + secondNumber
            "-" -> result = firstNumber - secondNumber
            "*" -> result = firstNumber * secondNumber
            "/" -> {
                result = if (secondNumber == 0.0) {
                    Double.NaN
                } else {
                    firstNumber / secondNumber
                }
            }
            else -> binding.operation.setText("")
        }
        binding.result.setText(result.toString())
        binding.newNumber.setText("")
        binding.operation.setText("")
    }
}
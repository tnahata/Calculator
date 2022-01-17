package com.example.android.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        hideKeyboard()

        val listener = View.OnClickListener { v ->
            val b = v as Button
            if (binding.result.hasFocus())
                numberListenerCode(binding.result, b)
            else if (binding.newNumber.hasFocus())
                numberListenerCode(binding.newNumber, b)
        }

        val operatorListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            if (binding.operation.hasFocus())
                binding.operation.setText(op)
        }

        setButtonListeners(listener, operatorListener)

        binding.buttonNeg.setOnClickListener {
            if (binding.result.hasFocus())
                negativeButtonCode(binding.result)
            else if (binding.newNumber.hasFocus())
                negativeButtonCode(binding.newNumber)
        }

        binding.buttonClr.setOnClickListener {
            val num: String
            when {
                binding.newNumber.hasFocus() -> {
                    num = binding.newNumber.text.toString()
                    binding.newNumber.setText(num.dropLast(1))
                }
                binding.result.hasFocus() -> {
                    num = binding.result.text.toString()
                    binding.result.setText(num.dropLast(1))
                }
                binding.operation.hasFocus() -> {
                    binding.operation.setText("")
                }
            }
        }

        binding.buttonEquals.setOnClickListener {
            try {
                val firstNumber = binding.result.text.toString().toDouble()
                val secondNumber = binding.newNumber.text.toString().toDouble()
                val operator = binding.operation.text.toString()
                calculateResult(firstNumber, secondNumber, operator)
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    this@MainActivity, "Enter a valid number!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideKeyboard() {
        binding.result.showSoftInputOnFocus = false
        binding.newNumber.showSoftInputOnFocus = false
        binding.operation.showSoftInputOnFocus = false
    }

    private fun numberListenerCode(numberField: EditText, button: Button) {
        if (numberField.text.isEmpty()) {
            if (button.text == ".") {
                numberField.setText("0.")
            } else {
                numberField.text.append(button.text)
            }
        } else {
            numberField.text.append(button.text)
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

    private fun setButtonListeners(listener: View.OnClickListener, operatorListener: View.OnClickListener) {
        binding.apply {
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
            buttonPlus.setOnClickListener(operatorListener)
            buttonMinus.setOnClickListener(operatorListener)
            buttonDivide.setOnClickListener(operatorListener)
            buttonMultiply.setOnClickListener(operatorListener)
        }
    }

    private fun calculateResult(firstNumber: Double, secondNumber: Double, operator: String) {
        val result: Double
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
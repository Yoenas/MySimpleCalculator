package com.yoenas.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var tvResult: TextView
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tv_result)
    }

    fun onDigit(view: View) {
        tvResult.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvResult.setText("")
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvResult.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvResult.text.toString())) {
            tvResult.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")

        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = tvResult.text.toString()
            var prefix = ""
            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }
                if (value.contains("-")) {
                    val splitValue = value.split("-")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvResult.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if (value.contains("/")) {
                    val splitValue = value.split("/")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvResult.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if (value.contains("*")) {
                    val splitValue = value.split("*")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvResult.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                } else if (value.contains("+")) {
                    val splitValue = value.split("+")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvResult.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}
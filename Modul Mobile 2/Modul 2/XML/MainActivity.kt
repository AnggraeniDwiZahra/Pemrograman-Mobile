package com.example.calculatortip
//xml ver

import android.os.Bundle
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val editBillAmount: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.editBillAmount)
        val spinnerTipOptions: android.widget.Spinner = findViewById(R.id.spinnerTipOptions)
        val switchRoundUp: androidx.appcompat.widget.SwitchCompat = findViewById(R.id.switchRoundUp)
        val textTipResult: android.widget.TextView = findViewById(R.id.textTipResult)

        fun triggerUpdate() {
            calculateTip(editBillAmount, spinnerTipOptions, switchRoundUp, textTipResult)
        }


        spinnerTipOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                triggerUpdate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            triggerUpdate()
        }
        editBillAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                triggerUpdate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun calculateTip(
        editBillAmount: TextInputEditText,
        spinnerTipOptions: Spinner,
        switchRoundUp: androidx.appcompat.widget.SwitchCompat,
        textTipResult: TextView
    ) {
        val stringInTextField = editBillAmount.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        //jika input kosong
        if (cost == null) {
            textTipResult.text = "Tip Amount: $0.00"
            return
        }
        val tipPercentage = when (spinnerTipOptions.selectedItemPosition) {
            0 -> 0.15
            1 -> 0.18
            else -> 0.20
        }
        var tip = tipPercentage * cost

        if (switchRoundUp.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        textTipResult.text = "Tip Amount: $formattedTip"
    }
}
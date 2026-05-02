package com.example.mycalculatorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculatorapp.api.RetrofitClient
import com.example.mycalculatorapp.model.CurrencyItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyConverterActivity : AppCompatActivity() {

    // Views
    private lateinit var btnBack: ImageButton
    private lateinit var etAmount1: EditText
    private lateinit var tvResult2: TextView
    private lateinit var tvResult3: TextView

    private lateinit var tvCurrencySelector1: TextView
    private lateinit var tvCurrencySelector2: TextView
    private lateinit var tvCurrencySelector3: TextView
    private lateinit var tvName1: TextView
    private lateinit var tvName2: TextView
    private lateinit var tvName3: TextView

    // State
    private lateinit var selectedCurrency1: CurrencyItem
    private lateinit var selectedCurrency2: CurrencyItem
    private lateinit var selectedCurrency3: CurrencyItem
    private var conversionJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        // Initialize Views
        btnBack = findViewById(R.id.btn_back)
        etAmount1 = findViewById(R.id.et_amount_1)
        tvResult2 = findViewById(R.id.tv_result_2)
        tvResult3 = findViewById(R.id.tv_result_3)
        tvCurrencySelector1 = findViewById(R.id.tv_currency_selector_1)
        tvCurrencySelector2 = findViewById(R.id.tv_currency_selector_2)
        tvCurrencySelector3 = findViewById(R.id.tv_currency_selector_3)
        tvName1 = findViewById(R.id.tv_name_1)
        tvName2 = findViewById(R.id.tv_name_2)
        tvName3 = findViewById(R.id.tv_name_3)

        btnBack.setOnClickListener { finish() }

        setupDefaultCurrencies()
        setupKeypad()
        setupClickListeners()
    }

    private fun setupDefaultCurrencies() {
        selectedCurrency1 = CurrencyItem("PKR", "Pakistani Rupee", "🇵🇰")
        selectedCurrency2 = CurrencyItem("USD", "United States Dollar", "🇺🇸")
        selectedCurrency3 = CurrencyItem("EUR", "Euro", "🇪🇺")
        updateCurrencyViews()
    }

    private fun updateCurrencyViews() {
        tvCurrencySelector1.text = "${selectedCurrency1.flag} ${selectedCurrency1.code}"
        tvName1.text = selectedCurrency1.name
        tvCurrencySelector2.text = "${selectedCurrency2.flag} ${selectedCurrency2.code}"
        tvName2.text = selectedCurrency2.name
        tvCurrencySelector3.text = "${selectedCurrency3.flag} ${selectedCurrency3.code}"
        tvName3.text = selectedCurrency3.name
        triggerConversion()
    }

    private fun setupKeypad() {
        val digitIds = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
            R.id.btn_00
        )
        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onDigit((it as Button).text.toString()) }
        }
        findViewById<Button>(R.id.btn_dot).setOnClickListener { onDot() }
        findViewById<Button>(R.id.btn_ac).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btn_backspace).setOnClickListener { onBackspace() }
    }

    private fun setupClickListeners() {
        val resultLauncher1 = createCurrencyLauncher { currency ->
            selectedCurrency1 = currency
            updateCurrencyViews()
        }
        val resultLauncher2 = createCurrencyLauncher { currency ->
            selectedCurrency2 = currency
            updateCurrencyViews()
        }
        val resultLauncher3 = createCurrencyLauncher { currency ->
            selectedCurrency3 = currency
            updateCurrencyViews()
        }

        tvCurrencySelector1.setOnClickListener {
            resultLauncher1.launch(Intent(this, CurrencySelectionActivity::class.java))
        }
        tvCurrencySelector2.setOnClickListener {
            resultLauncher2.launch(Intent(this, CurrencySelectionActivity::class.java))
        }
        tvCurrencySelector3.setOnClickListener {
            resultLauncher3.launch(Intent(this, CurrencySelectionActivity::class.java))
        }
    }

    private fun createCurrencyLauncher(onResult: (CurrencyItem) -> Unit) = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val selectedCurrency = data?.getSerializableExtra("selected_currency") as? CurrencyItem
            if (selectedCurrency != null) {
                onResult(selectedCurrency)
            }
        }
    }

    // Keypad and Conversion Logic (unchanged from before) 

    private fun onDigit(d: String) {
        val currentText = etAmount1.text.toString()
        if (currentText == "0") {
             if (d != "0" && d != "00") etAmount1.setText(d)
        } else {
            etAmount1.append(d)
        }
        triggerConversion()
    }

    private fun onBackspace() {
        val currentText = etAmount1.text.toString()
        if (currentText.isNotEmpty()) {
            etAmount1.setText(currentText.dropLast(1))
            if (etAmount1.text.isEmpty()) etAmount1.setText("0")
            triggerConversion()
        }
    }

    private fun onDot() {
         val currentText = etAmount1.text.toString()
         if (!currentText.contains(".")) {
             etAmount1.append(".")
         }
    }

    private fun onClear() {
        etAmount1.setText("0")
        tvResult2.text = "0.00"
        tvResult3.text = "0.00"
    }

    private fun triggerConversion() {
        val amountStr = etAmount1.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0
        
        conversionJob?.cancel()
        conversionJob = CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getLatestRates(selectedCurrency1.code)
                }

                if (response.isSuccessful && response.body()?.result == "success") {
                    val rates = response.body()?.conversionRates ?: emptyMap()
                    
                    val rate2 = rates[selectedCurrency2.code] ?: 0.0
                    val rate3 = rates[selectedCurrency3.code] ?: 0.0
                    
                    tvResult2.text = String.format("%.4f", amount * rate2)
                    tvResult3.text = String.format("%.4f", amount * rate3)
                } else {
                    tvResult2.text = "Error"
                    tvResult3.text = "Error"
                }
            } catch (e: Exception) {
                 e.printStackTrace()
            }
        }
    }
}

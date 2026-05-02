package com.example.mycalculatorapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculatorapp.adapter.CurrencyAdapter
import com.example.mycalculatorapp.model.CurrencyItem

class CurrencySelectionActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var etSearch: EditText
    private lateinit var rvCurrencies: RecyclerView
    private lateinit var adapter: CurrencyAdapter

    private val currencyList = listOf(
        CurrencyItem("PKR", "Pakistani Rupee", "🇵🇰"),
        CurrencyItem("USD", "United States Dollar", "🇺🇸"),
        CurrencyItem("EUR", "Euro", "🇪🇺"),
        CurrencyItem("GBP", "British Pound", "🇬🇧"),
        CurrencyItem("JPY", "Japanese Yen", "🇯🇵"),
        CurrencyItem("INR", "Indian Rupee", "🇮🇳"),
        CurrencyItem("CAD", "Canadian Dollar", "🇨🇦"),
        CurrencyItem("AUD", "Australian Dollar", "🇦🇺"),
        CurrencyItem("CHF", "Swiss Franc", "🇨🇭"),
        CurrencyItem("CNY", "Chinese Yuan", "🇨🇳"),
        CurrencyItem("SAR", "Saudi Riyal", "🇸🇦"),
        CurrencyItem("AED", "UAE Dirham", "🇦🇪"),
        CurrencyItem("TRY", "Turkish Lira", "🇹🇷"),
        CurrencyItem("RUB", "Russian Ruble", "🇷🇺"),
        CurrencyItem("BRL", "Brazilian Real", "🇧🇷"),
        CurrencyItem("ZAR", "South African Rand", "🇿🇦"),
        CurrencyItem("MYR", "Malaysian Ringgit", "🇲🇾"),
        CurrencyItem("SGD", "Singapore Dollar", "🇸🇬"),
        CurrencyItem("NZD", "New Zealand Dollar", "🇳🇿"),
        CurrencyItem("SEK", "Swedish Krona", "🇸🇪"),
        CurrencyItem("NOK", "Norwegian Krone", "🇳🇴"),
        CurrencyItem("DKK", "Danish Krone", "🇩🇰"),
        CurrencyItem("KRW", "South Korean Won", "🇰🇷"),
        CurrencyItem("MXN", "Mexican Peso", "🇲🇽"),
        CurrencyItem("HKD", "Hong Kong Dollar", "🇭🇰"),
        CurrencyItem("THB", "Thai Baht", "🇹🇭"),
        CurrencyItem("IDR", "Indonesian Rupiah", "🇮🇩"),
        CurrencyItem("PHP", "Philippine Peso", "🇵🇭"),
        CurrencyItem("PLN", "Polish Zloty", "🇵🇱"),
        CurrencyItem("ILS", "Israeli Shekel", "🇮🇱"),
        CurrencyItem("EGP", "Egyptian Pound", "🇪🇬"),
        CurrencyItem("VND", "Vietnamese Dong", "🇻🇳"),
        CurrencyItem("ARS", "Argentine Peso", "🇦🇷"),
        CurrencyItem("CLP", "Chilean Peso", "🇨🇱"),
        CurrencyItem("CZK", "Czech Koruna", "🇨🇿"),
        CurrencyItem("HUF", "Hungarian Forint", "🇭🇺"),
        CurrencyItem("KWD", "Kuwaiti Dinar", "🇰🇼"),
        CurrencyItem("BHD", "Bahraini Dinar", "🇧🇭"),
        CurrencyItem("OMR", "Omani Rial", "🇴🇲"),
        CurrencyItem("QAR", "Qatari Riyal", "🇶🇦"),
        CurrencyItem("RON", "Romanian Leu", "🇷🇴"),
        CurrencyItem("BGN", "Bulgarian Lev", "🇧🇬"),
        CurrencyItem("HRK", "Croatian Kuna", "🇭🇷"),
        CurrencyItem("BDT", "Bangladeshi Taka", "🇧🇩"),
        CurrencyItem("LKR", "Sri Lankan Rupee", "🇱🇰"),
        CurrencyItem("NPR", "Nepalese Rupee", "🇳🇵"),
        CurrencyItem("NGN", "Nigerian Naira", "🇳🇬"),
        CurrencyItem("KES", "Kenyan Shilling", "🇰🇪"),
        CurrencyItem("TWD", "Taiwan Dollar", "🇹🇼"),
        CurrencyItem("UAH", "Ukrainian Hryvnia", "🇺🇦"),
        CurrencyItem("COP", "Colombian Peso", "🇨🇴"),
        CurrencyItem("PEN", "Peruvian Sol", "🇵🇪"),
        CurrencyItem("MAD", "Moroccan Dirham", "🇲🇦"),
        CurrencyItem("JOD", "Jordanian Dinar", "🇯🇴"),
        CurrencyItem("GEL", "Georgian Lari", "🇬🇪"),
        CurrencyItem("ISK", "Icelandic Krona", "🇮🇸"),
        CurrencyItem("RSD", "Serbian Dinar", "🇷🇸"),
        CurrencyItem("TND", "Tunisian Dinar", "🇹🇳"),
        CurrencyItem("UYU", "Uruguayan Peso", "🇺🇾"),
        CurrencyItem("KZT", "Kazakhstani Tenge", "🇰🇿")
    ).sortedBy { it.name }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_currency_selection)

        // 1. Initialize Views FIRST (Crucial Step)
        btnBack = findViewById(R.id.btn_back)
        etSearch = findViewById(R.id.et_search)
        rvCurrencies = findViewById(R.id.rv_currencies)

        // 2. Apply Window Insets to Root (Top padding for Status Bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_selection)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Only set Top Padding. Leave Bottom as 0
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // 3. Apply Window Insets to RecyclerView (Bottom padding for Navigation Bar)
        ViewCompat.setOnApplyWindowInsetsListener(rvCurrencies) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Add bottom padding to the RecyclerView content so the last item scrolls up above the nav bar
            // Added extra +50 to ensure it clears comfortably
            v.setPadding(0, 0, 0, systemBars.bottom + 50)
            insets
        }

        // 4. Setup UI logic
        btnBack.setOnClickListener { finish() }

        adapter = CurrencyAdapter(currencyList) { selectedCurrency ->
            val resultIntent = Intent().apply {
                putExtra("selected_currency", selectedCurrency)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        rvCurrencies.layoutManager = LinearLayoutManager(this)
        rvCurrencies.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
        })
    }
}

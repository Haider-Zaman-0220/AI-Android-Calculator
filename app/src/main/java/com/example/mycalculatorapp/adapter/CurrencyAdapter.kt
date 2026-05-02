package com.example.mycalculatorapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculatorapp.R
import com.example.mycalculatorapp.model.CurrencyItem

class CurrencyAdapter(
    private val currencies: List<CurrencyItem>,
    private val onItemClick: (CurrencyItem) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var filteredCurrencies = currencies.toList()

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFlag: TextView = itemView.findViewById(R.id.tv_flag)
        val tvName: TextView = itemView.findViewById(R.id.tv_currency_name)
        val tvCode: TextView = itemView.findViewById(R.id.tv_currency_code)

        fun bind(currency: CurrencyItem) {
            tvFlag.text = currency.flag
            tvName.text = currency.name
            tvCode.text = currency.code
            itemView.setOnClickListener { onItemClick(currency) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(filteredCurrencies[position])
    }

    override fun getItemCount(): Int = filteredCurrencies.size

    fun filter(query: String) {
        filteredCurrencies = if (query.isEmpty()) {
            currencies
        } else {
            currencies.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.code.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}

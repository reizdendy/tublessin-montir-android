package com.example.tublessin_montir.recyleview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.transaction.Transaction

class TransactionHistoryAdapter(private val transactionList: List<Transaction>):
    RecyclerView.Adapter<TransactionHistoryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_history_recycle_view, parent, false)
            return TransactionHistoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
            holder.transactionId.text = "Transaction ID : ${transactionList[position].id}"
            holder.customerName.text = "Customer : ${transactionList[position].user_firstname}"
            if (transactionList[position].status == "Canceled") {
                holder.status.setTextColor(Color.RED)
                holder.header.setCardBackgroundColor(Color.RED)
            } else if (transactionList[position].status == "Success") {
                holder.status.setTextColor(Color.parseColor("#43C639"))
                holder.header.setCardBackgroundColor(Color.parseColor("#43C639"))
            } else {
                holder.status.setTextColor(Color.BLUE)
                holder.header.setCardBackgroundColor(Color.BLUE)
            }
            holder.status.text = transactionList[position].status
            holder.date.text = transactionList[position].date_created
        }

        override fun getItemCount(): Int {
            return transactionList.size
        }
}

class TransactionHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val transactionId = view.findViewById<TextView>(R.id.transactionId)
    val customerName = view.findViewById<TextView>(R.id.customerName)
    val status = view.findViewById<TextView>(R.id.statusTransaction)
    val date = view.findViewById<TextView>(R.id.dateTransaction)
    var header = view.findViewById<CardView>(R.id.header_color_accent_trans_recycle_view)
}
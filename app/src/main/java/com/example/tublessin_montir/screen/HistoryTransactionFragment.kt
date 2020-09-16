package com.example.tublessin_montir.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.example.tublessin_montir.domain.transaction.TransactionViewModel
import com.example.tublessin_montir.recyleview.TransactionHistoryAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_history_transaction.*

class HistoryTransactionFragment : Fragment() {


    private val transactionViewModel = TransactionViewModel()
    private lateinit var montirId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        montirId = Prefs.getString("id", "0")

        transaction_history_recycle_view.layoutManager = LinearLayoutManager(this.context)
        transactionViewModel.RequestMontirTransactionList(montirId)
        transactionViewModel.transactionList().observe(viewLifecycleOwner, Observer{
            if(!it.Results.results.isNullOrEmpty()) {
                transaction_history_recycle_view.adapter = TransactionHistoryAdapter(it.Results.results)
            }
        })
    }
}
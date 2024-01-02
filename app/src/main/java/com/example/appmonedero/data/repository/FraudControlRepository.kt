package com.example.appmonedero.data.repository

import com.example.appmonedero.data.model.Transaction

class FraudControlRepository {
    companion object{
        var dataset: MutableList<Transaction> = mutableListOf()
        const val FRAUD_LIMIT : Int = 1000

        init {
            initDataset()
        }

        private fun initDataset(): MutableList<Transaction> {
            return dataset
        }

        fun addTransaction(transaction: Transaction){
            dataset.add(transaction)
        }
    }
}
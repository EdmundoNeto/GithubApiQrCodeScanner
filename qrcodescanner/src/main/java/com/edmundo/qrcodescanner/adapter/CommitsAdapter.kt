package com.edmundo.qrcodescanner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edmundo.domain.model.CommitResponse
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.holder.CommitsViewHolder
import com.edmundo.qrcodescanner.holder.QrScannerViewHolder

class CommitsAdapter: RecyclerView.Adapter<QrScannerViewHolder>() {

    private var commitsList: List<CommitResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrScannerViewHolder =
        CommitsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commit_item, null)
        )

    override fun onBindViewHolder(holder: QrScannerViewHolder, position: Int) {
        val commit = commitsList[position]

        holder.bind(commitsList[position])
    }

    override fun getItemCount(): Int = commitsList.count()

    fun setCommitList(list: List<CommitResponse>) {
        this.commitsList = list
        notifyDataSetChanged()
    }
}
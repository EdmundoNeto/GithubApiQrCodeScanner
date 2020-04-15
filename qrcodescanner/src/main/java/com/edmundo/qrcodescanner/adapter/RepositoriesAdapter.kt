package com.edmundo.qrcodescanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.edmundo.domain.model.Repository
import com.edmundo.qrcodescanner.BR
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.holder.QrScannerViewHolder
import com.edmundo.qrcodescanner.holder.RepositoriesViewHolder
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoriesAdapter(private val itemClickAction: (Repository) -> Unit):
    RecyclerView.Adapter<QrScannerViewHolder>() {

    private var repositoryList: List<Repository> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrScannerViewHolder =
        RepositoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repository_item, null)
        )

    override fun onBindViewHolder(holder: QrScannerViewHolder, position: Int) {
        val event = repositoryList[position]

        holder.apply {

            itemView.setOnClickListener {
                itemClickAction.invoke(event)
            }
        }
        holder.bind(repositoryList[position])
    }

    override fun getItemCount(): Int = repositoryList.count()

    fun setRepositoryList(list: List<Repository>) {
        this.repositoryList = list
        notifyDataSetChanged()
    }
}

//class RepositoriesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//
//    fun binding(repository: Repository) =
//        with(view) {
//            tvRepositoryName.text = repository.name.orEmpty()
//            tvRepositoryDescription.text = repository.description.orEmpty()
//            tvLanguage.text = repository.language.orEmpty()
//        }
//
//}
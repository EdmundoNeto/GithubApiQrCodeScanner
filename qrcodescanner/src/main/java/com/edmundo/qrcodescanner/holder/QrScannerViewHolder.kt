package com.edmundo.qrcodescanner.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.model.Repository
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.extensions.longDateTimeString
import com.edmundo.qrcodescanner.extensions.toDate
import kotlinx.android.synthetic.main.commit_item.view.*
import kotlinx.android.synthetic.main.repository_item.view.*

abstract class QrScannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(T: Any)
}

class RepositoriesViewHolder(private val view: View) : QrScannerViewHolder(view) {
    override fun bind(T: Any) =
        with(view) {
            tvRepositoryName.text = (T as Repository).name.orEmpty()
            tvRepositoryDescription.text = T.description.orEmpty()
            tvLanguage.text = T.language.orEmpty()
        }
}

class CommitsViewHolder(private val view: View) : QrScannerViewHolder(view) {
    override fun bind(T: Any) =
        with(view) {
            tvCommitTitle.text = (T as CommitResponse).commit?.message.orEmpty()
            tvCommitAuthor.text = T.commit?.committer?.name.orEmpty()
            tvCommitDate.text = context.getString(R.string.commited_at,
                T.commit?.committer?.date?.toDate()?.longDateTimeString().orEmpty()
            )
        }
}


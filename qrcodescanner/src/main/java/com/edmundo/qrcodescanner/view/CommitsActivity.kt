package com.edmundo.qrcodescanner.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.adapter.CommitsAdapter
import com.edmundo.qrcodescanner.extensions.observe
import com.edmundo.qrcodescanner.viewmodel.CommitsViewModel
import kotlinx.android.synthetic.main.activity_commits.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CommitsActivity: AppCompatActivity() {

    private val username by lazy { intent.getStringExtra("username").orEmpty() }
    private val repoName by lazy { intent.getStringExtra("repository").orEmpty() }

    private val viewModel: CommitsViewModel by viewModel()
    private val listAdapter: CommitsAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)

        supportActionBar?.title = getString(R.string.commit_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lError_btRefresh.setOnClickListener { viewModel.getReposCommits(username, repoName) }
    }

    private fun setupObservable() {
        viewModel.getReposCommits(username, repoName)

        viewModel.run {
            observe(commitsList) {
                it?.run {
                    listAdapter.setCommitList(
                        this.take(5).sortedByDescending { it.commit?.committer?.date }
                    )
                }
            }
            observe(contendVisibility) {
                it?.run {
                    rvCommits.visibility = this
                }
            }
            observe(errorVisibility) {
                it?.run {
                    layoutError.visibility = this
                }
            }
            observe(mainLoaderVisibility) {
                it?.run {
                    loadingLayout.visibility = this
                }
            }
            observe(noComitsFoundVisibility) {
                it?.run {
                    tvCommitsNotFount.visibility = this
                }
            }

        }
    }

    private fun setupRecycler() {
        rvCommits.apply {
            adapter = listAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
        setupObservable()
    }

}
package com.edmundo.qrcodescanner.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edmundo.domain.model.Repository
import com.edmundo.qrcodescanner.R
import com.edmundo.qrcodescanner.adapter.RepositoriesAdapter
import com.edmundo.qrcodescanner.extensions.observe
import com.edmundo.qrcodescanner.viewmodel.RepositoriesViewModel
import kotlinx.android.synthetic.main.activity_repositories.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RepositoriesActivity: AppCompatActivity() {

    private val username by lazy { intent.getStringExtra("username").orEmpty() }
    private val viewModel: RepositoriesViewModel by viewModel()
    private val listAdapter: RepositoriesAdapter by inject {
        parametersOf(
            { repo: Repository ->
                startActivity(
                    Intent(this, CommitsActivity::class.java)
                        .putExtra("username", username)
                        .putExtra("repository", repo.name)
                )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        supportActionBar?.title = getString(R.string.repositorys_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lError_btRefresh.setOnClickListener { viewModel.getData(username) }
    }

    private fun setupObservable() {
        viewModel.getData(username)

        viewModel.run {
            observe(reposList) {
                it?.run {
                    listAdapter.setRepositoryList(this)
                }
            }
            observe(contendVisibility) {
                it?.run {
                    main.visibility = this
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
            observe(noReposFoundVisibility) {
                it?.run {
                    tvReposNotFount.visibility = this
                }
            }

        }
    }

    private fun setupRecycler() {
        main.apply {
            this.adapter = listAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onStart() {
        super.onStart()
        setupRecycler()
        setupObservable()
    }

}
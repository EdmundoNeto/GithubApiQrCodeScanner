package com.edmundo.domain.datasource

import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.model.Repository
import com.edmundo.domain.model.User

interface QrCodeScannerContract {

    suspend fun getUser(username: String): User
    suspend fun getRepositories(username: String): List<Repository>
    suspend fun getCommits(owner: String, repository: String): List<CommitResponse>

}
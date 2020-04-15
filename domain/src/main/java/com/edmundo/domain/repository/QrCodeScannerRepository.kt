package com.edmundo.domain.repository

import com.edmundo.domain.SafeRequest
import com.edmundo.domain.datasource.QrCodeScannerContract
import com.edmundo.domain.datasource.QrCodeScannerService
import com.edmundo.domain.model.CommitResponse
import com.edmundo.domain.model.Repository
import com.edmundo.domain.model.User

class QrCodeScannerRepository(private val service: QrCodeScannerService) :
    QrCodeScannerContract,
    SafeRequest() {

    override suspend fun getUser(username: String): User = apiRequest {
        service.getUser(username)
    }

    override suspend fun getRepositories(username: String): List<Repository> = apiRequest {
        service.getRepositories(username)
    }

    override suspend fun getCommits(owner: String, repository: String): List<CommitResponse> = apiRequest {
        service.getCommits(owner, repository)
    }
}
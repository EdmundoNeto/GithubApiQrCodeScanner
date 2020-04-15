package com.edmundo.domain.model

data class CommitResponse(
    val comments_url: String? = null,
    val commit: Commit? = null,
    val html_url: String? = null,
    val node_id: String? = null,
    val sha: String? = null,
    val url: String? = null
)

data class Commit(
    val author: Author? = null,
    val committer: Committer? = null,
    val message: String? = null,
    val tree: Tree? = null,
    val url: String? = null
)

data class Author(
    val date: String? = null,
    val email: String? = null,
    val name: String? = null
)

data class Committer(
    val date: String? = null,
    val email: String? = null,
    val name: String? = null
)

data class Tree(
    val sha: String? = null,
    val url: String? = null
)
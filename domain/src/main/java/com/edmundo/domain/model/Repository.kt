package com.edmundo.domain.model

data class Repository(
    var full_name: String? = null,
    var html_url: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var node_id: String? = null,
    var language: String? = null,
    var description: String? = null
)
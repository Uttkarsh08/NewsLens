package com.example.mynews.data.remote.dto

import com.example.mynews.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
package com.example.mynews.domain.usecases.news

import androidx.paging.PagingData
import com.example.mynews.domain.model.Article
import com.example.mynews.domain.repository.NewsRepository
import com.example.mynews.presentation.search.SearchEvent
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(searchQuery: String, sources: List<String>) : Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}
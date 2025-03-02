package com.example.mynews.di

import android.app.Application
import com.example.mynews.data.manager.LocalUserManagerImpl
import com.example.mynews.data.remote.NewsApi
import com.example.mynews.data.repository.NewsRepositoryImpl
import com.example.mynews.domain.manager.LocalUserManager
import com.example.mynews.domain.repository.NewsRepository
import com.example.mynews.domain.usecases.app_entry.AppEntryUseCases
import com.example.mynews.domain.usecases.app_entry.ReadAppEntry
import com.example.mynews.domain.usecases.app_entry.SaveAppEntry
import com.example.mynews.domain.usecases.news.GetNews
import com.example.mynews.domain.usecases.news.NewsUseCases
import com.example.mynews.domain.usecases.news.SearchNews
import com.example.mynews.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager = localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager = localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository = newsRepository),
            searchNews = SearchNews(newsRepository = newsRepository)
        )
    }
}
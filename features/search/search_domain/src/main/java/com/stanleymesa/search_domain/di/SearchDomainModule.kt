package com.stanleymesa.search_domain.di

import com.stanleymesa.search_domain.repository.SearchRepository
import com.stanleymesa.search_domain.use_case.GetSearchPaging
import com.stanleymesa.search_domain.use_case.SearchUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchDomainModule {

    @Provides
    fun provideDomainUseCases(
        repository: SearchRepository
    ): SearchUseCases = SearchUseCases(
        getSearchPaging = GetSearchPaging(repository),
    )
}
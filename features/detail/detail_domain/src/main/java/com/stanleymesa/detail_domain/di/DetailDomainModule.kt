package com.stanleymesa.detail_domain.di

import com.stanleymesa.detail_domain.repository.DetailRepository
import com.stanleymesa.detail_domain.use_case.DetailUseCases
import com.stanleymesa.detail_domain.use_case.GetUser
import com.stanleymesa.detail_domain.use_case.GetUserRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DetailDomainModule {

    @Provides
    fun provideDomainUseCases(
        repository: DetailRepository
    ): DetailUseCases = DetailUseCases(
        getUser = GetUser(repository),
        getUserRepos = GetUserRepos(repository)
    )
}
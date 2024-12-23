package com.example.sumitwork.di

import com.example.sumitwork.network.BaseService
import com.example.sumitwork.repository.Repository
import com.example.sumitwork.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesRepository(
        remote: BaseService,
    ): Repository = RepositoryImpl(
        remote
    )
}
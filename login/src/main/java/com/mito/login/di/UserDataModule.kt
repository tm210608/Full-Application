package com.mito.login.di

import com.mito.database.data.dao.UserDao
import com.mito.login.data.UserDataRepositoryImpl
import com.mito.login.data.UserDataSourceImpl
import com.mito.login.domain.UserDataRepository
import com.mito.login.domain.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserDataModule {
    @Provides
    @Singleton
    fun provideUserDataRepository(dataSource: UserDataSource): UserDataRepository {
        return UserDataRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userDao: UserDao): UserDataSource {
        return UserDataSourceImpl(userDao)
    }
}
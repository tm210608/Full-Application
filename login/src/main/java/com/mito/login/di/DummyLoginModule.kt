package com.mito.login.di

import com.mito.database.data.dao.UserDao
import com.mito.login.data.DummyLoginDataSource
import com.mito.login.data.DummyLoginDataSourceImpl
import com.mito.login.data.DummyLoginRepository
import com.mito.login.data.DummyLoginRepositoryImpl
import com.mito.network.dummy_login.data.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DummyLoginModule {

    @Provides
    @Singleton
    fun provideDummyLoginDataSource(
        loginService: LoginService
    ): DummyLoginDataSource {
        return DummyLoginDataSourceImpl(loginService)
    }

    @Provides
    @Singleton
    fun provideDummyLoginRepository(dataSource: DummyLoginDataSource, userDao: UserDao): DummyLoginRepository {
        return DummyLoginRepositoryImpl(dataSource, userDao)
    }
}
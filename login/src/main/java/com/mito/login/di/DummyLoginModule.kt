package com.mito.login.di

import com.mito.login.data.DummyLoginDataSourceImpl
import com.mito.login.data.DummyLoginRepositoryImpl
import com.mito.login.domain.DummyLoginDataSource
import com.mito.login.domain.DummyLoginRepository
import com.mito.login.domain.UserDataSource
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
    fun provideDummyLoginRepository(loginDataSource: DummyLoginDataSource, userDataSource: UserDataSource): DummyLoginRepository {
        return DummyLoginRepositoryImpl(loginDataSource, userDataSource)
    }
}
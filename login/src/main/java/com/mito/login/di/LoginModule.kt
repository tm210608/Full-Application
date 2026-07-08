package com.mito.login.di

import com.mito.login.data.LoginDataSourceImpl
import com.mito.login.data.LoginRepositoryImpl
import com.mito.login.domain.LoginDataSource
import com.mito.login.domain.LoginRepository
import com.mito.login.domain.UserDataSource
import com.mito.network.auth.data.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginDataSource(
        loginService: LoginService
    ): LoginDataSource {
        return LoginDataSourceImpl(loginService)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginDataSource: LoginDataSource, userDataSource: UserDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource, userDataSource)
    }
}
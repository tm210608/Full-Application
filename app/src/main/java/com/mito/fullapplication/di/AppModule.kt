package com.mito.fullapplication.di

import com.mito.core.navigation.ScreenProvider
import com.mito.fullapplication.navigation.AppScreenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    
    @Provides
    @Singleton
    fun provideScreenProvider(): ScreenProvider = AppScreenProvider()
}
package com.mito.database.di

import android.content.Context
import androidx.room.Room
import com.mito.database.data.FullApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): FullApplicationDatabase {
        return Room.databaseBuilder(
            context,
            FullApplicationDatabase::class.java,
            "full_application_database"
            ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: FullApplicationDatabase) = database.userDao()

}
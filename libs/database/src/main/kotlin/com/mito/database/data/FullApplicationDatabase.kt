package com.mito.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mito.database.data.dao.UserDao
import com.mito.database.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class FullApplicationDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}
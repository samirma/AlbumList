package com.samir.albumlist.di

import android.content.Context
import androidx.room.Room
import com.samir.albumlist.data.local.AlbumDao
import com.samir.albumlist.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "room_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }

}

package com.samir.albumlist.di

import com.samir.albumlist.data.local.AlbumDao
import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.AlbumLocalRepositoryImpl
import com.samir.albumlist.data.remote.AlbumApiService
import com.samir.albumlist.data.remote.AlbumRemoteRepository
import com.samir.albumlist.data.remote.AlbumRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlbumRemoteRepository(apiService: AlbumApiService): AlbumRemoteRepository {
        return AlbumRemoteRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAlbumLocalRepository(
        albumDao: AlbumDao
    ): AlbumLocalRepository {
        return AlbumLocalRepositoryImpl(albumDao)
    }

}

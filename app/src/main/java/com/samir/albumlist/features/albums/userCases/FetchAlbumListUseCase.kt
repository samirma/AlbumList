package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.remote.AlbumRemoteRepository
import com.samir.albumlist.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchAlbumListUseCase @Inject constructor(
    private val saveAlbums: SaveAlbumsUseCase,
    private val remoteRepository: AlbumRemoteRepository,
    private val isOnlineUseCase: IsOnlineUseCase,
    private val isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() {
        withContext(dispatcher) {
            if (isOnlineUseCase().first() && !isDatabasePopulatedUseCase()) {
                val response = remoteRepository.getAlbums().getOrThrow()
                saveAlbums(
                    albumList = response
                )
            }
        }
    }

}
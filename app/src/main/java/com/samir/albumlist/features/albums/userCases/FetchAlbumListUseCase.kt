package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.remote.AlbumRemoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FetchAlbumListUseCase @Inject constructor(
    private val saveAlbums: SaveAlbumsUseCase,
    private val remoteRepository: AlbumRemoteRepository,
    private val isOnlineUseCase: IsOnlineUseCase,
    private val isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase,
) {

    suspend operator fun invoke() {

        if (isOnlineUseCase().first() && !isDatabasePopulatedUseCase()) {
            val response = remoteRepository.getAlbums().getOrThrow()
            saveAlbums(
                albumList = response
            )
        }
    }

}
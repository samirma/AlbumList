package com.samir.albumlist.features.albums

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.data.remote.AlbumRemoteRepository
import com.samir.albumlist.features.albums.userCases.SaveAlbumsUseCase

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator(
    private val saveAlbums: SaveAlbumsUseCase,
    private val remoteRepository: AlbumRemoteRepository,
) : RemoteMediator<Int, AlbumPhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumPhotoEntity>
    ): MediatorResult {
        return when (loadType) {
            LoadType.REFRESH -> {
                try {
                    val response = remoteRepository.getAlbums().getOrThrow()
                    saveAlbums(
                        albumList = response,
                        shouldClearDataBase = true
                    )
                    MediatorResult.Success(endOfPaginationReached = true)
                } catch (e: Exception) {
                    MediatorResult.Error(e)
                }
            }
            LoadType.PREPEND, LoadType.APPEND -> {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }
}

package com.samir.albumlist.features.albums

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.userCases.FetchAlbumListUseCase
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator @Inject constructor(
    private val fetchAlbumList: FetchAlbumListUseCase
) : RemoteMediator<Int, AlbumPhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumPhotoEntity>
    ): MediatorResult {
        return when (loadType) {
            LoadType.REFRESH -> {
                try {
                    fetchAlbumList()
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

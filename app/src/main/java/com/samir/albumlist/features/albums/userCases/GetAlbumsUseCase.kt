package com.samir.albumlist.features.albums.userCases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.data.remote.AlbumRemoteRepository
import com.samir.albumlist.features.albums.AlbumRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetAlbumsUseCase @Inject constructor(
    private val localRepository: AlbumLocalRepository,
    private val remoteRepository: AlbumRemoteRepository,
    private val saveAlbums: SaveAlbumsUseCase,
) {
    operator fun invoke(): Flow<PagingData<AlbumPhotoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = AlbumRemoteMediator(
                saveAlbums = saveAlbums,
                remoteRepository = remoteRepository,
            ),
            pagingSourceFactory = {
                localRepository.getPagedAlbums()
            }
        ).flow
    }
    companion object {
        const val PAGE_SIZE = 50
    }
}

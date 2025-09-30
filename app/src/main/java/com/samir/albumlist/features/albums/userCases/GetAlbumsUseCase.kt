package com.samir.albumlist.features.albums.userCases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.di.DefaultDispatcher
import com.samir.albumlist.features.albums.AlbumRemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetAlbumsUseCase @Inject constructor(
    private val localRepository: AlbumLocalRepository,
    private val mediator: AlbumRemoteMediator,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<PagingData<AlbumPhotoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = mediator,
            pagingSourceFactory = {
                localRepository.getPagedAlbums()
            }
        ).flow.flowOn(dispatcher)
    }
    companion object {
        const val PAGE_SIZE = 50
    }
}

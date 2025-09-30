package com.samir.albumlist.data.local

import androidx.paging.PagingSource
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import javax.inject.Inject

class AlbumLocalRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
) : AlbumLocalRepository {
    override fun getPagedAlbums(): PagingSource<Int, AlbumPhotoEntity> {
        return albumDao.getAll()
    }

    override suspend fun insertAll(entities: List<AlbumPhotoEntity>) {
        albumDao.insertAll(entities)
    }

    override suspend fun getTotalItems(): Long {
        return albumDao.getTotalItems()
    }
}

package com.samir.albumlist.data.local

import androidx.paging.PagingSource
import com.samir.albumlist.data.local.model.AlbumPhotoEntity

interface AlbumLocalRepository {
    fun getPagedAlbums(): PagingSource<Int, AlbumPhotoEntity>
    suspend fun insertAll(entities: List<AlbumPhotoEntity>)
    suspend fun getTotalItems(): Long
}

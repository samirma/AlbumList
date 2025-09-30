package com.samir.albumlist.data.remote

import com.samir.albumlist.data.remote.model.AlbumPhotoRemote

interface AlbumRemoteRepository {
    suspend fun getAlbums(): Result<List<AlbumPhotoRemote>>
}

package com.samir.albumlist.data.remote

import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import javax.inject.Inject

class AlbumRemoteRepositoryImpl @Inject constructor(
    private val apiService: AlbumApiService
) : AlbumRemoteRepository {
    override suspend fun getAlbums(): Result<List<AlbumPhotoRemote>> {
        return runCatching {
            val response = apiService.getAlbums()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                throw Exception(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }
}

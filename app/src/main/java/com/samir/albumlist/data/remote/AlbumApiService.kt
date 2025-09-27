package com.samir.albumlist.data.remote

import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApiService {
    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<AlbumPhotoRemote>>
}

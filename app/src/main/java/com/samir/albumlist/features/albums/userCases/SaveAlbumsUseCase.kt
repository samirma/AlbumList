package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAlbumsUseCase @Inject constructor(
    private val localRepository: AlbumLocalRepository,
) {
    suspend operator fun invoke(
        albumList: List<AlbumPhotoRemote>
    ) {
        localRepository.insertAll(
            albumList.map { it.toAlbumPhotoEntity() }
        )
    }

    private fun AlbumPhotoRemote.toAlbumPhotoEntity() = AlbumPhotoEntity(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}

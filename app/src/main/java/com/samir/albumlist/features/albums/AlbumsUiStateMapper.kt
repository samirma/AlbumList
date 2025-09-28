package com.samir.albumlist.features.albums

import androidx.paging.PagingData
import androidx.paging.map
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.ui.AlbumItemView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumsUiStateMapper @Inject constructor() {
    operator fun invoke(
        albumsFlow: Flow<PagingData<AlbumPhotoEntity>>,
        isOffline: Boolean
    ): AlbumsUiState {
        return AlbumsUiState.Loaded(
            albums = albumsFlow.map { pagingData ->
                pagingData.map { it.toAlbum() }
            },
            isOffline = isOffline
        )
    }

    private fun AlbumPhotoEntity.toAlbum(): AlbumItemView {
        return AlbumItemView(
            id = id,
            title = title,
            thumbnailUrl = thumbnailUrl
        )
    }
}

package com.samir.albumlist.features.albums

import androidx.paging.PagingData
import androidx.paging.map
import com.samir.albumlist.R
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.ui.AlbumItemView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumsUiStateMapper @Inject constructor() {

    suspend operator fun invoke(
        albumsFlow: Flow<PagingData<AlbumPhotoEntity>>,
        isOnline: Boolean,
        isDatabasePopulated: Boolean
    ): AlbumsUiState = withContext(Dispatchers.Default) {
        if (!isOnline && !isDatabasePopulated) {
            AlbumsUiState.Error(R.string.internet_required)
        } else AlbumsUiState.Loaded(
            albums = albumsFlow.map { pagingData ->
                pagingData.map { it.toAlbum() }
            },
            isOnline = isOnline
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

package com.samir.albumlist.features.albums
import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.samir.albumlist.features.albums.ui.AlbumItemView
import kotlinx.coroutines.flow.Flow

sealed class AlbumsUiState {
    data class Loaded(
        val albums: Flow<PagingData<AlbumItemView>>,
        val isOnline: Boolean
    ) : AlbumsUiState()

    data class Error(
        @StringRes val message: Int,
    ) : AlbumsUiState()

    data object Loading : AlbumsUiState()
}

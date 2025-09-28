package com.samir.albumlist.features.albums.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.samir.albumlist.R
import com.samir.albumlist.features.common.ui.EmptyState
import com.samir.albumlist.features.common.ui.ErrorState
import com.samir.albumlist.features.common.ui.LoadingState
import kotlinx.coroutines.flow.Flow

@Composable
fun AlbumListScreen(
    albums: Flow<PagingData<AlbumItemView>>,
    onAlbumClick: (Int) -> Unit
) {
    val albumItems = albums.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val loadState = albumItems.loadState.refresh) {
            is LoadState.Loading -> LoadingState()
            is LoadState.Error -> {
                val message = loadState.error.localizedMessage ?: stringResource(R.string.an_error_occurred)
                ErrorState(message = message)
            }
            else -> {
                if (albumItems.itemCount == 0) {
                    EmptyState()
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(albumItems.itemCount, key = { index -> albumItems[index]?.id ?: index }) { index ->
                            albumItems[index]?.let { album ->
                                AlbumItem(
                                    album = album,
                                    onSelect = { onAlbumClick(album.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

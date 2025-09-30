package com.samir.albumlist.features.albums.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.samir.albumlist.R
import com.samir.albumlist.features.albums.AlbumsUiState
import com.samir.albumlist.features.albums.AlbumsViewModel
import com.samir.albumlist.features.common.ui.ErrorState
import com.samir.albumlist.features.common.ui.LoadingState
import com.samir.albumlist.features.common.ui.OfflineBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsNavigation(
    viewModel: AlbumsViewModel,
    onAlbumClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is AlbumsUiState.Error -> ErrorState(message = stringResource(id = state.message))
                is AlbumsUiState.Loaded -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (!state.isOnline) {
                            OfflineBanner()
                        }
                        AlbumListScreen(
                            albums = state.albums,
                            onAlbumClick = onAlbumClick
                        )
                    }
                }

                AlbumsUiState.Loading -> LoadingState()
            }
        }
    }
}

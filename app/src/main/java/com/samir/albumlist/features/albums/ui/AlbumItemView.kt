package com.samir.albumlist.features.albums.ui

import androidx.compose.runtime.Stable

@Stable
data class AlbumItemView(
    val id: Int,
    val title: String,
    val thumbnailUrl: String
)

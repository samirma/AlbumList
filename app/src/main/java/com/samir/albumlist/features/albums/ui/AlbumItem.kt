package com.samir.albumlist.features.albums.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.samir.albumlist.features.common.ui.theme.AlbumListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumItem(album: AlbumItemView, onSelect: (AlbumItemView) -> Unit) {
    Card(
        onClick = { onSelect(album) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = album.thumbnailUrl,
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = album.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumItemPreview() {
    AlbumListTheme {
        AlbumItem(
            album = AlbumItemView(
                id = 1,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
            ),
            onSelect = {}
        )
    }
}

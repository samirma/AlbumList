package com.samir.albumlist.features

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.samir.albumlist.features.albums.AlbumsViewModel
import com.samir.albumlist.features.albums.ui.AlbumsNavigation
import com.samir.albumlist.features.common.ui.theme.AlbumListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content() {
    AlbumListTheme {
        val viewModel = hiltViewModel<AlbumsViewModel>()
        val context = LocalContext.current
        AlbumsNavigation(
            viewModel = viewModel,
            onAlbumClick = { albumId ->
                Toast.makeText(context, "Clicked on album $albumId", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

package com.samir.albumlist.features.albums

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samir.albumlist.R
import com.samir.albumlist.features.albums.userCases.GetAlbumsUseCase
import com.samir.albumlist.features.albums.userCases.IsDatabasePopulatedUseCase
import com.samir.albumlist.features.albums.userCases.IsOnlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsUseCase: GetAlbumsUseCase,
    isOnlineUseCase: IsOnlineUseCase,
    private val isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase,
    private val mapper: AlbumsUiStateMapper
) : ViewModel() {

    val uiState: StateFlow<AlbumsUiState> = isOnlineUseCase()
        .map { isOnline->
            val isDatabasePopulated = isDatabasePopulatedUseCase()
            mapper(
                albumsFlow = getAlbumsUseCase(isOnline = isOnline),
                isOnline = isOnline,
                isDatabasePopulated = isDatabasePopulated
            )
        }
        .catch {
            Log.e("AlbumsViewModel", "Error in UI state flow", it)
            emit(AlbumsUiState.Error(R.string.error_loading_albums))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AlbumsUiState.Loading
        )
}

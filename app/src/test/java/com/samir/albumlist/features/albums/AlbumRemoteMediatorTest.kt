package com.samir.albumlist.features.albums

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.userCases.FetchAlbumListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediatorTest {

    @RelaxedMockK
    private lateinit var fetchAlbumListUseCase: FetchAlbumListUseCase

    @InjectMockKs
    private lateinit var victim: AlbumRemoteMediator

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `load returns success and fetches albums on refresh`() = runTest {
        // Given
        val pagingState = PagingState<Int, AlbumPhotoEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = androidx.paging.PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        // When
        val result = victim.load(LoadType.REFRESH, pagingState)

        // Then
        coVerify { fetchAlbumListUseCase() }
        assert(result is androidx.paging.RemoteMediator.MediatorResult.Success)
    }
}


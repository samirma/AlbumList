package com.samir.albumlist.features.albums

import androidx.paging.PagingData
import com.samir.albumlist.R
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AlbumsUiStateMapperTest {

    @InjectMockKs
    private lateinit var victim: AlbumsUiStateMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when online and database is populated, should return Loaded state`() = runTest {
        // Given
        val albumsFlow = flowOf(
            PagingData.from(
                listOf(
                    AlbumPhotoEntity(
                        albumId = 1,
                        id = 1,
                        title = "title",
                        url = "url",
                        thumbnailUrl = "thumb"
                    )
                )
            )
        )

        // When
        val result = victim(albumsFlow, isOnline = true, isDatabasePopulated = true)

        // Then
        assertTrue(result is AlbumsUiState.Loaded)
        assertEquals(true, (result as AlbumsUiState.Loaded).isOnline)
    }

    @Test
    fun `when offline and database is not populated, should return Error state`() = runTest {
        // Given
        val albumsFlow = flowOf(PagingData.empty<AlbumPhotoEntity>())

        // When
        val result = victim(albumsFlow, isOnline = false, isDatabasePopulated = false)

        // Then
        assertTrue(result is AlbumsUiState.Error)
        assertEquals(R.string.internet_required, (result as AlbumsUiState.Error).message)
    }
}


package com.samir.albumlist.data.local

import androidx.paging.PagingSource
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AlbumLocalRepositoryImplTest {

    @MockK
    private lateinit var albumDao: AlbumDao

    @InjectMockKs
    private lateinit var victim: AlbumLocalRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getPagedAlbums returns a PagingSource`() {
        // Given
        val pagingSource = mockk<PagingSource<Int, AlbumPhotoEntity>>()
        coEvery { albumDao.getAll() } returns pagingSource

        // When
        val result = victim.getPagedAlbums()

        // Then
        assertEquals(pagingSource, result)
    }

    @Test
    fun `insertAll inserts albums`() = runBlocking {
        // Given
        val albumPhotoEntity =
            AlbumPhotoEntity(1, 1, "title", "url", "thumbnailUrl")
        val entities = listOf(albumPhotoEntity)

        coEvery { albumDao.insertAll(entities) } just Runs

        // When
        victim.insertAll(entities)

        // Then
        coVerify { albumDao.insertAll(entities) }
    }

    @Test
    fun `getTotalItems returns total count`() = runBlocking {
        // Given
        val totalItems = 1L
        coEvery { albumDao.getTotalItems() } returns totalItems

        // When
        val result = victim.getTotalItems()

        // Then
        assertEquals(totalItems, result)
    }
}

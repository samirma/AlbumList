package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveAlbumsUseCaseTest {

    @MockK
    private lateinit var albumLocalRepository: AlbumLocalRepository

    @InjectMockKs
    private lateinit var victim: SaveAlbumsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke saves albums`() = runBlocking {
        // Given
        val albumPhotoRemote =
            AlbumPhotoRemote(1, 1, "title", "url", "thumbnailUrl")
        val albumList = listOf(albumPhotoRemote)

        // When
        victim.invoke(albumList)

        // Then
        coVerify { albumLocalRepository.insertAll(any()) }
    }
}


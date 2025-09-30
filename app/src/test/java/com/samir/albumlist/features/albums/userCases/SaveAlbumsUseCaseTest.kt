package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveAlbumsUseCaseTest {

    @MockK
    private lateinit var albumLocalRepository: AlbumLocalRepository

    private lateinit var victim: SaveAlbumsUseCase

    private lateinit var dispatcher: CoroutineDispatcher

    @Before
    fun setUp() {
        dispatcher = UnconfinedTestDispatcher()
        MockKAnnotations.init(this)
        victim = SaveAlbumsUseCase(albumLocalRepository, dispatcher)
    }

    @Test
    fun `invoke saves albums`() = runBlocking {
        // Given
        val albumPhotoRemote =
            AlbumPhotoRemote(1, 1, "title", "url", "thumbnailUrl")
        val albumList = listOf(albumPhotoRemote)
        coEvery { albumLocalRepository.insertAll(any()) } just Runs

        // When
        victim.invoke(albumList)

        // Then
        coVerify { albumLocalRepository.insertAll(any()) }
    }
}

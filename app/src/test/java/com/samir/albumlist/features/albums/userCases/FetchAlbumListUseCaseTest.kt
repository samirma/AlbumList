package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.remote.AlbumRemoteRepository
import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAlbumListUseCaseTest {

    @MockK
    private lateinit var saveAlbums: SaveAlbumsUseCase

    @MockK
    private lateinit var remoteRepository: AlbumRemoteRepository

    @MockK
    private lateinit var isOnlineUseCase: IsOnlineUseCase

    @MockK
    private lateinit var isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    @InjectMockKs
    private lateinit var victim: FetchAlbumListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke fetches and saves albums when online and database is not populated`() = runTest {
        // Given
        val albumPhotoRemote = AlbumPhotoRemote(1, 1, "title", "url", "thumbnailUrl")
        val albumList = listOf(albumPhotoRemote)
        coEvery { isOnlineUseCase() } returns flowOf(true)
        coEvery { isDatabasePopulatedUseCase() } returns false
        coEvery { remoteRepository.getAlbums() } returns Result.success(albumList)
        coEvery { saveAlbums(any()) } just Runs

        // When
        victim()

        // Then
        coVerify { saveAlbums(albumList) }
    }

    @Test
    fun `invoke does not fetch and save albums when offline`() = runTest {
        // Given
        coEvery { isOnlineUseCase() } returns flowOf(false)
        coEvery { isDatabasePopulatedUseCase() } returns false

        // When
        victim()

        // Then
        coVerify(exactly = 0) { remoteRepository.getAlbums() }
        coVerify(exactly = 0) { saveAlbums(any()) }
    }

    @Test
    fun `invoke does not fetch and save albums when database is populated`() = runTest {
        // Given
        coEvery { isOnlineUseCase() } returns flowOf(true)
        coEvery { isDatabasePopulatedUseCase() } returns true

        // When
        victim()

        // Then
        coVerify(exactly = 0) { remoteRepository.getAlbums() }
        coVerify(exactly = 0) { saveAlbums(any()) }
    }
}

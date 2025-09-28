package com.samir.albumlist.features.albums

import androidx.paging.PagingData
import app.cash.turbine.test
import com.samir.albumlist.R
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.ui.AlbumItemView
import com.samir.albumlist.features.albums.userCases.GetAlbumsUseCase
import com.samir.albumlist.features.albums.userCases.IsDatabasePopulatedUseCase
import com.samir.albumlist.features.albums.userCases.IsOnlineUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getAlbumsUseCase: GetAlbumsUseCase

    @RelaxedMockK
    private lateinit var isOnlineUseCase: IsOnlineUseCase

    @RelaxedMockK
    private lateinit var isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase

    @RelaxedMockK
    private lateinit var mapper: AlbumsUiStateMapper

    @InjectMockKs
    private lateinit var victim: AlbumsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `uiState should be Loaded when data is available`() = runTest {
        // Given
        val albumEntity = AlbumPhotoEntity(1, 1, "Test", "url", "thumbnail")
        val pagingData = PagingData.from(listOf(albumEntity))

        val pagingDataView = PagingData.from(
            listOf(
                AlbumItemView(
                    id = 1,
                    title = "Test",
                    thumbnailUrl = "thumbnail"
                )
            )
        )

        every { getAlbumsUseCase() } returns flowOf(pagingData)
        every { isOnlineUseCase() } returns flowOf(true)
        coEvery { isDatabasePopulatedUseCase() } returns true
        coEvery { mapper(any(), any(), any()) } returns AlbumsUiState.Loaded(
            flowOf(pagingDataView),
            true
        )


        // Then
        victim.uiState.test {
            val state = awaitItem()
            assertTrue(state is AlbumsUiState.Loaded)
        }
    }

    @Test
    fun `uiState should be Error when there is an exception`() = runTest {
        // Given
        val errorMessage = "Network error"
        every { getAlbumsUseCase() } returns flow { throw Exception(errorMessage) }
        every { isOnlineUseCase() } returns flowOf(true)
        coEvery { isDatabasePopulatedUseCase() } returns true

        // Then
        victim.uiState.test {
            val state = awaitItem()
            assertTrue(state is AlbumsUiState.Error)
        }
    }

    @Test
    fun `uiState should be Error when offline and database is not populated`() = runTest {
        // Given
        every { getAlbumsUseCase() } returns flowOf(PagingData.empty())
        every { isOnlineUseCase() } returns flowOf(false)
        coEvery { isDatabasePopulatedUseCase() } returns false
        coEvery {
            mapper(
                any(),
                false,
                false
            )
        } returns AlbumsUiState.Error(R.string.internet_required)

        // Then
        victim.uiState.test {
            val state = awaitItem()
            assertTrue(state is AlbumsUiState.Error)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


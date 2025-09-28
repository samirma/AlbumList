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
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var getAlbumsUseCase: GetAlbumsUseCase

    @MockK
    private lateinit var isOnlineUseCase: IsOnlineUseCase

    @MockK
    private lateinit var isDatabasePopulatedUseCase: IsDatabasePopulatedUseCase

    @MockK
    private lateinit var mapper: AlbumsUiStateMapper

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
        val albumItemView = AlbumItemView(id = 1, title = "Test", thumbnailUrl = "thumbnail")
        val pagingDataView = PagingData.from(listOf(albumItemView))
        val loadedState = AlbumsUiState.Loaded(flowOf(pagingDataView), true)

        every { getAlbumsUseCase() } returns flowOf(pagingData)
        every { isOnlineUseCase() } returns flowOf(true)
        coEvery { isDatabasePopulatedUseCase() } returns true
        coEvery { mapper(any(), isOnline = true, isDatabasePopulated = true) } returns loadedState

        // When
        victim = AlbumsViewModel(getAlbumsUseCase, isOnlineUseCase, isDatabasePopulatedUseCase, mapper)

        // Then
        victim.uiState.test {
            // Skip initial Loading state
            skipItems(1)
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

        // When
        victim = AlbumsViewModel(getAlbumsUseCase, isOnlineUseCase, isDatabasePopulatedUseCase, mapper)

        // Then
        victim.uiState.test {
            // Skip initial Loading state
            skipItems(1)
            val state = awaitItem()
            assertTrue(state is AlbumsUiState.Error)
        }
    }

    @Test
    fun `uiState should be Error when offline and database is not populated`() = runTest {
        // Given
        val errorState = AlbumsUiState.Error(R.string.internet_required)
        every { getAlbumsUseCase() } returns flowOf(PagingData.empty())
        every { isOnlineUseCase() } returns flowOf(false)
        coEvery { isDatabasePopulatedUseCase() } returns false
        coEvery { mapper(any(), isOnline = false, isDatabasePopulated = false) } returns errorState

        // When
        victim = AlbumsViewModel(getAlbumsUseCase, isOnlineUseCase, isDatabasePopulatedUseCase, mapper)

        // Then
        victim.uiState.test {
            // Skip initial Loading state
            skipItems(1)
            val state = awaitItem()
            assertTrue(state is AlbumsUiState.Error)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

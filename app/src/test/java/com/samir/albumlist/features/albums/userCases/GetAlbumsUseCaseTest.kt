package com.samir.albumlist.features.albums.userCases

import androidx.paging.PagingSource
import app.cash.turbine.test
import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.AlbumRemoteMediator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAlbumsUseCaseTest {

    @MockK
    private lateinit var localRepository: AlbumLocalRepository

    @RelaxedMockK
    private lateinit var mediator: AlbumRemoteMediator

    @InjectMockKs
    private lateinit var victim: GetAlbumsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke returns flow of paged albums`() = runTest {
        // Given
        val pagingSource: PagingSource<Int, AlbumPhotoEntity> = mockk(relaxed = true)
        every { localRepository.getPagedAlbums() } returns pagingSource

        // When
        val result = victim.invoke()

        // Then
        result.test{
            val item = awaitItem()
            assertNotNull(item)
        }
    }
}


package com.samir.albumlist.features.albums.userCases

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.samir.albumlist.data.local.AlbumLocalRepository
import com.samir.albumlist.data.local.model.AlbumPhotoEntity
import com.samir.albumlist.features.albums.AlbumRemoteMediator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAlbumsUseCaseTest {

    @MockK
    private lateinit var localRepository: AlbumLocalRepository

    @MockK
    private lateinit var mediator: AlbumRemoteMediator

    @MockK
    private lateinit var pagingSource: PagingSource<Int, AlbumPhotoEntity>

    @InjectMockKs
    private lateinit var victim: GetAlbumsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke returns flow of paged albums`() = runBlocking {
        // Given
        every { localRepository.getPagedAlbums() } returns pagingSource

        // When
        val result = victim.invoke().first()

        // Then
        assert(result is PagingData<AlbumPhotoEntity>)
    }
}


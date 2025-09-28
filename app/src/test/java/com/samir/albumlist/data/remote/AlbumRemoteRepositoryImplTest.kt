package com.samir.albumlist.data.remote

import com.samir.albumlist.data.remote.model.AlbumPhotoRemote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AlbumRemoteRepositoryImplTest {

    @MockK
    private lateinit var albumApiService: AlbumApiService

    @InjectMockKs
    private lateinit var victim: AlbumRemoteRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getAlbums returns success`() = runBlocking {
        // Given
        val albumPhotoRemote = AlbumPhotoRemote(1, 1, "title", "url", "thumbnailUrl")
        val albumPhotos = listOf(albumPhotoRemote)
        val successResponse: Response<List<AlbumPhotoRemote>> =
            Response.success(albumPhotos)

        coEvery {
            albumApiService.getAlbums()
        } returns successResponse

        // When
        val result = victim.getAlbums()

        // Then
        assertEquals(Result.success(albumPhotos), result)
    }

    @Test
    fun `getAlbums returns failure`() = runBlocking {
        // Given
        val errorResponse: Response<List<AlbumPhotoRemote>> =
            Response.error(404, "".toResponseBody(null))

        coEvery {
            albumApiService.getAlbums()
        } returns errorResponse

        // When
        val result = victim.getAlbums()

        // Then
        assert(result.isFailure)
    }
}

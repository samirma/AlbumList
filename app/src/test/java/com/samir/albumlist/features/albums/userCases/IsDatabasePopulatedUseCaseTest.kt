package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.local.AlbumLocalRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IsDatabasePopulatedUseCaseTest {

    @MockK
    private lateinit var localRepository: AlbumLocalRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @InjectMockKs
    private lateinit var victim: IsDatabasePopulatedUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `invoke returns true when database is populated`() = runTest {
        // Given
        coEvery { localRepository.getTotalItems() } returns 1

        // When
        val result = victim()

        // Then
        assertTrue(result)
    }

    @Test
    fun `invoke returns false when database is not populated`() = runTest {
        // Given
        coEvery { localRepository.getTotalItems() } returns 0

        // When
        val result = victim()

        // Then
        assertFalse(result)
    }
}


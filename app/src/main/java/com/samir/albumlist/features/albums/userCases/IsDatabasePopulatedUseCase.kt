package com.samir.albumlist.features.albums.userCases

import com.samir.albumlist.data.local.AlbumLocalRepository
import javax.inject.Inject

class IsDatabasePopulatedUseCase @Inject constructor(
    private val localRepository: AlbumLocalRepository
) {
    suspend operator fun invoke(): Boolean {
        return localRepository.getTotalItems() > 0
    }
}

package com.samir.albumlist.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samir.albumlist.data.local.model.AlbumPhotoEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumPhotoEntity>)

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun getAll(): PagingSource<Int, AlbumPhotoEntity>

    @Query("DELETE FROM albums")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM albums")
    suspend fun getTotalItems(): Long
}

package com.samir.albumlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samir.albumlist.data.local.model.AlbumPhotoEntity

@Database(entities = [AlbumPhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}

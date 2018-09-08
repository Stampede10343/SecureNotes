package com.dev.cameronc.securenotes.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [RoomNote::class, RoomBulletedNote::class, RoomBulletItem::class], version = 1)
abstract class RoomNotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
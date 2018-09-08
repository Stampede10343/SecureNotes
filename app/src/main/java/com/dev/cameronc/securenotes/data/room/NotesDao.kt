package com.dev.cameronc.securenotes.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface NotesDao {

    @Query("SELECT * FROM roomnote")
    fun getNotes(): Flowable<List<RoomNote>>

    @Query("SELECT * FROM roomnote WHERE id = :id")
    fun getNote(id: Long): Flowable<RoomNote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNote(note: RoomNote): Long

    @Update
    fun updateNote(note: RoomNote)

    @Delete
    fun deleteNote(note: RoomNote)

    @Query("SELECT * FROM roombulletednote WHERE id = :noteId")
    fun getBulletedNote(noteId: Long): Flowable<RoomBulletedNote>
}
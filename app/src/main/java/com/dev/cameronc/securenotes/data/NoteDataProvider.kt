package com.dev.cameronc.securenotes.data

import io.reactivex.Flowable
import io.reactivex.Observable

interface NoteDataProvider {
    fun getNotes(): Flowable<List<Note>>
    fun getNote(id: Long): Flowable<Note>
    fun createNote(note: Note): Long
    fun updateNote(note: Note)
    fun deleteNote(note: Note): Observable<NoteDeleteEvent>
    fun getBulletedNote(noteId: Long): Flowable<BulletedNote>
}
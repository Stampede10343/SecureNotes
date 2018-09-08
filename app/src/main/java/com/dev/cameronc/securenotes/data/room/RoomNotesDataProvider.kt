package com.dev.cameronc.securenotes.data.room

import com.dev.cameronc.securenotes.data.BulletedNote
import com.dev.cameronc.securenotes.data.Note
import com.dev.cameronc.securenotes.data.NoteDataProvider
import com.dev.cameronc.securenotes.data.NoteDeleteEvent
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class RoomNotesDataProvider @Inject constructor(private val notesDao: NotesDao, private val roomNoteMapper: RoomNoteMapper) : NoteDataProvider {

    override fun getNotes(): Flowable<List<Note>> {
        return notesDao.getNotes()
                .map { notesList ->
                    notesList.map { roomNote -> roomNoteMapper.toNote(roomNote) }
                }
    }

    override fun getNote(id: Long): Flowable<Note> {
        return notesDao.getNote(id)
                .map { roomNoteMapper.toNote(it) }
    }

    override fun createNote(note: Note): Long {
        return notesDao.createNote(roomNoteMapper.toRoomNote(note))
    }

    override fun updateNote(note: Note) {
        notesDao.updateNote(roomNoteMapper.toRoomNote(note))
    }

    override fun deleteNote(note: Note): Observable<NoteDeleteEvent> {
        notesDao.deleteNote(roomNoteMapper.toRoomNote(note))

        return Observable.just(NoteDeleteEvent())
    }

    override fun getBulletedNote(noteId: Long): Flowable<BulletedNote> {
        return notesDao.getBulletedNote(noteId)
                .map { BulletedNote(it.title, emptyList(), it.createDate, it.id) }
    }
}
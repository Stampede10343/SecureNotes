package com.dev.cameronc.securenotes.data.room

import com.dev.cameronc.securenotes.data.Note
import javax.inject.Inject

class RoomNoteMapper @Inject constructor() {
    fun toNote(note: RoomNote): Note {
        return Note(note.id, note.title, note.content, note.createDate)
    }

    fun toRoomNote(note: Note): RoomNote {
        return RoomNote(note.id, note.title, note.content, note.createDate)
    }
}

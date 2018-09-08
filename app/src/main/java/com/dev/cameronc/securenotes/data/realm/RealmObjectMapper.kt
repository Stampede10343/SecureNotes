package com.dev.cameronc.securenotes.data.realm

import com.dev.cameronc.securenotes.data.BulletItem
import com.dev.cameronc.securenotes.data.BulletedNote
import com.dev.cameronc.securenotes.data.Note
import javax.inject.Inject

class RealmObjectMapper @Inject constructor() {
    fun mapRealmNote(realmNote: RealmNote): Note {
        return Note(realmNote.id, realmNote.title, realmNote.content, realmNote.createDate)
    }

    fun mapNote(note: Note): RealmNote {
        val realmNote = RealmNote()
        realmNote.id = note.id
        realmNote.title = note.title
        realmNote.content = note.content
        realmNote.createDate = note.createDate

        return realmNote
    }

    fun mapRealmBulletedNote(realmBulletedNote: RealmBulletedNote): BulletedNote {
        val bullets: List<BulletItem> = realmBulletedNote.bulletItems.map { BulletItem(it.isChecked, it.text, it.id) }

        return BulletedNote(realmBulletedNote.title, bullets, realmBulletedNote.createDate, realmBulletedNote.id)
    }
}
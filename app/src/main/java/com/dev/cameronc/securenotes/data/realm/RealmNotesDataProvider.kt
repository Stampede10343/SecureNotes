package com.dev.cameronc.securenotes.data.realm

import com.dev.cameronc.securenotes.Schedulers
import com.dev.cameronc.securenotes.data.BulletedNote
import com.dev.cameronc.securenotes.data.Note
import com.dev.cameronc.securenotes.data.NoteDataProvider
import com.dev.cameronc.securenotes.data.NoteDeleteEvent
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class RealmNotesDataProvider @Inject constructor(private val realm: Realm, private val noteMapper: RealmObjectMapper,
                                                 private val schedulers: Schedulers) : NoteDataProvider {
    override fun getNotes(): Flowable<List<Note>> {
        return Flowable.just(realm.where(RealmNote::class.java).findAll().toList().map { note -> noteMapper.mapRealmNote(note) })
                .subscribeOn(schedulers.databaseScheduler)
                .observeOn(schedulers.uiScheduler)
    }

    override fun getNote(id: Long): Flowable<Note> {
        return Flowable.just(realm.where(RealmNote::class.java).equalTo("id", id).findFirst() ?: return Flowable.empty<Note>())
                .map { noteMapper.mapRealmNote(it) }
                .subscribeOn(schedulers.databaseScheduler)
                .observeOn(schedulers.uiScheduler)
    }

    override fun createNote(note: Note): Long {
        val realmNote = RealmNote()
        realmNote.id = note.id
        realmNote.content = note.content
        realmNote.createDate = note.createDate

        val firstNote: RealmNote? = realm.where(RealmNote::class.java)
                .equalTo("id", realmNote.id)
                .findFirst()
        val size = realm.where(RealmNote::class.java).max("id")?.toLong() ?: 0
        return if (firstNote == null) {
            realm.beginTransaction()
            realmNote.id = size + 1L
            val id = realm.copyToRealm(realmNote)
                    .id
            realm.commitTransaction()

            return id
        }
        else 0
    }

    override fun updateNote(note: Note) {
        realm.executeTransaction {
            val realmNote = it.where(RealmNote::class.java)
                    .equalTo("id", note.id)
                    .findFirst()
            if (realmNote != null) {
                realmNote.title = note.title
                realmNote.content = note.content
                realmNote.createDate = note.createDate
            }
        }
    }

    override fun deleteNote(note: Note): Observable<NoteDeleteEvent> {
        val result = realm.where(RealmNote::class.java)
                .equalTo("id", note.id)
                .findAll()
        realm.executeTransaction {
            result.deleteAllFromRealm()
        }
        return Observable.just(NoteDeleteEvent())
    }

    override fun getBulletedNote(noteId: Long): Flowable<BulletedNote> {
        return Flowable.just(realm.where(RealmBulletedNote::class.java).contains("id", noteId.toString()))
                .map { noteMapper.mapRealmBulletedNote(it.findFirst()!!) }
                .subscribeOn(schedulers.databaseScheduler)
                .observeOn(schedulers.uiScheduler)
    }
}
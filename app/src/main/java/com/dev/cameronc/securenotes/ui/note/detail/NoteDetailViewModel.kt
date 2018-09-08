package com.dev.cameronc.securenotes.ui.note.detail

import com.dev.cameronc.securenotes.Schedulers
import com.dev.cameronc.securenotes.data.Note
import com.dev.cameronc.securenotes.data.NoteDataProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(private val noteDataProvider: NoteDataProvider, private val schedulers: Schedulers) {
    private val noteSubject: BehaviorSubject<Note> = BehaviorSubject.create()
    private val disposables = CompositeDisposable()

    val noteChanged: Consumer<NoteDetailScreen.NoteChangedEvent> = Consumer { event ->
        val createDate = Date().time
        val note = Note(event.noteId, event.title, event.content, createDate)
        if (event.noteId == 0L) {
            Timber.i("Note created: %s", event.noteId)
            Timber.d("Current Thread: %s", Thread.currentThread().name)
            val id = noteDataProvider.createNote(note)
            noteSubject.onNext(Note(id, event.title, event.content, createDate))
        }
        else {
            Timber.i("Note updated: %s", event.noteId)
            noteDataProvider.updateNote(note)
            noteSubject.onNext(note)
        }
    }

    fun note(id: Long): Flowable<Note> {
        disposables.add(noteDataProvider.getNote(id).subscribe { note ->
            Timber.i("Note update pushed: %s", note)
            noteSubject.onNext(note)
        })
        return noteSubject.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun detach() {
        val note = noteSubject.value
        if (note != null && note.title.isEmpty() && note.content.isEmpty()) {
            disposables.add(noteDataProvider.deleteNote(note).subscribeOn(schedulers.databaseScheduler).observeOn(schedulers.uiScheduler).subscribe {
                disposables.clear()
            })
        }
    }

    fun deleteNote() {
        if (noteSubject.hasValue()) {
            disposables.add(noteDataProvider.deleteNote(noteSubject.value!!).subscribe())
        }
    }

    fun isBulletedNote(): Observable<Boolean> {
        return Observable.just(noteSubject.value is Note)
    }
}
package com.dev.cameronc.securenotes.ui.note

import com.dev.cameronc.securenotes.data.Note
import com.dev.cameronc.securenotes.data.NoteDataProvider
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class NoteListViewModel @Inject constructor(private val noteDataProvider: NoteDataProvider) {

    fun notes(): Flowable<List<Note>> = noteDataProvider.getNotes().observeOn(AndroidSchedulers.mainThread())
    fun shouldDisplayNoNotes(): Flowable<Boolean> = noteDataProvider.getNotes().map { notes -> notes.isEmpty() }
}
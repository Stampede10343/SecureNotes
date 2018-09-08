package com.dev.cameronc.securenotes.ui.note.bulletlist

import com.dev.cameronc.securenotes.data.BulletItem
import com.dev.cameronc.securenotes.data.NoteDataProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class BulletListViewModel @Inject constructor(private val noteDataProvider: NoteDataProvider) {
    private val bulletListItemsSubject: BehaviorSubject<List<BulletItem>> = BehaviorSubject.create()

    fun listItems(noteId: Long = 0): Observable<List<BulletItem>> {
        if (noteId == 0L) {

        }
        else {
            noteDataProvider.getBulletedNote(noteId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.bullets }
                    .subscribe({ notes -> bulletListItemsSubject.onNext(notes) })
        }
        return bulletListItemsSubject.observeOn(AndroidSchedulers.mainThread())
    }
}
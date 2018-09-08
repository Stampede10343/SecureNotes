package com.dev.cameronc.securenotes.ui.note.detail

import android.content.Context
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.Schedulers
import com.dev.cameronc.securenotes.SecureNotesApplication
import com.dev.cameronc.securenotes.data.BulletItem
import com.dev.cameronc.securenotes.data.NoteType
import com.dev.cameronc.securenotes.ui.ToolbarWrapper
import com.dev.cameronc.securenotes.ui.note.bulletlist.BulletRecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestack.navigator.StateKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.SegueViewChangeHandler
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.note_detail_screen.view.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NoteDetailScreen(context: Context, attributeSet: AttributeSet, styleAttr: Int) : LinearLayoutCompat(context, attributeSet,
        styleAttr), Backstack.CompletionListener {
    @Inject
    lateinit var noteDetailViewModel: NoteDetailViewModel
    @Inject
    lateinit var toolbarWrapper: ToolbarWrapper
    @Inject
    lateinit var schedulers: Schedulers
    private lateinit var noteDetailKey: NoteDetailKey
    private val disposables = CompositeDisposable()

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    override fun onFinishInflate() {
        SecureNotesApplication.activityComponent.inject(this)
        super.onFinishInflate()
        Navigator.getBackstack(context)
                .addCompletionListener(this)

        noteDetailKey = Backstack.getKey(context)
        toolbarWrapper.addMenuIcon("Delete", R.drawable.ic_delete_white_24dp) {
            noteDetailViewModel.deleteNote()
            disposables.clear()
            Navigator.getBackstack(context)
                    .goBack()
            true
        }

        val bulletListAdapter = BulletRecyclerView.BulletListAdapter()
        bulletListAdapter.setItems(listOf(BulletItem(false, "Hello dude", 0)))
        note_detail_bullet_list.adapter = bulletListAdapter
        note_detail_bullet_list.layoutManager = LinearLayoutManager(context, VERTICAL, false)

        disposables.add(noteDetailViewModel.isBulletedNote().subscribe { isBulletedNote ->
            //note_detail_bullet_list.visibility = if (isBulletedNote) View.VISIBLE else View.GONE
        })

        disposables.add(
                noteDetailViewModel.note(noteDetailKey.noteId).subscribeOn(schedulers.databaseScheduler).observeOn(schedulers.uiScheduler).subscribe { note ->
                    noteDetailKey = NoteDetailKey(note.id, NoteType.Note)
                    toolbarWrapper.setText(note.title)
                    if (note_detail_title.text.isEmpty()) note_detail_title.setText(note.title)
                    if (note_detail_content.text.isEmpty()) note_detail_content.setText(note.content)
                })

        disposables.add(Observable.combineLatest(RxTextView.afterTextChangeEvents(note_detail_title), RxTextView.afterTextChangeEvents(note_detail_content),
                BiFunction<TextViewAfterTextChangeEvent, TextViewAfterTextChangeEvent, NoteChangedEvent> { title, content ->
                    NoteChangedEvent(noteDetailKey.noteId, title.editable().toString(), content.editable().toString())
                }).distinctUntilChanged().debounce(300, TimeUnit.MILLISECONDS).subscribeOn(schedulers.databaseScheduler).observeOn(
                schedulers.uiScheduler).subscribe(noteDetailViewModel.noteChanged))

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
        noteDetailViewModel.detach()
        Timber.v("Screen detached")
    }

    override fun stateChangeCompleted(stateChange: StateChange) {
        if (stateChange.direction == StateChange.BACKWARD) {
            toolbarWrapper.clearMenu()
            Navigator.getBackstack(context)
                    .removeCompletionListener(this)
        }
    }

    data class NoteChangedEvent(val noteId: Long, val title: String, val content: String)

    @Parcelize
    data class NoteDetailKey(val noteId: Long, val noteType: NoteType) : StateKey, Parcelable {
        override fun layout(): Int = R.layout.note_detail_screen
        override fun viewChangeHandler(): ViewChangeHandler = SegueViewChangeHandler()
    }
}
package com.dev.cameronc.securenotes.ui.note

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.SecureNotesApplication
import com.dev.cameronc.securenotes.data.Note
import com.dev.cameronc.securenotes.data.NoteType
import com.dev.cameronc.securenotes.ui.ToolbarWrapper
import com.dev.cameronc.securenotes.ui.note.detail.NoteDetailScreen
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestack.navigator.StateKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.NoOpViewChangeHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.note_list_screen.view.*
import javax.inject.Inject

class NoteListScreen(context: Context, attrs: AttributeSet, styleAttrs: Int) : FrameLayout(context, attrs, styleAttrs), NotesListController.NoteClickListener {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val notesListController = NotesListController(this)
    @Inject
    lateinit var noteListViewModel: NoteListViewModel
    @Inject
    lateinit var actionBar: ToolbarWrapper

    init {
        SecureNotesApplication.activityComponent.inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        fab_add_note.setOnClickListener {
            note_list_fab.collapse()
            Navigator.getBackstack(context)
                    .goTo(NoteDetailScreen.NoteDetailKey(0, NoteType.Note))
        }

        fab_add_bullet_note.setOnClickListener {
            note_list_fab.collapse()
            Navigator.getBackstack(context)
                    .goTo(NoteDetailScreen.NoteDetailKey(0, NoteType.BulletNote))
        }

        actionBar.setText("Notes List")
        notes_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        notes_recycler_view.adapter = notesListController.adapter

        noteListViewModel.notes()
                .subscribe { notes -> notesListController.setNotes(notes) }

        noteListViewModel.shouldDisplayNoNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { shouldDisplayEmptyNotes ->
                    if (shouldDisplayEmptyNotes) {
                        notes_recycler_view.visibility = View.GONE
                        notes_list_no_notes.visibility = View.VISIBLE
                    }
                    else {
                        notes_recycler_view.visibility = View.VISIBLE
                        notes_list_no_notes.visibility = View.GONE
                    }
                }

        note_list_fab.setOnClickListener {
            Navigator.getBackstack(context)
                    .goTo(NoteDetailScreen.NoteDetailKey(0, NoteType.Note))
        }
    }

    override fun onNoteClicked(note: Note) {
        Navigator.getBackstack(context)
                .goTo(NoteDetailScreen.NoteDetailKey(note.id, NoteType.Note))
    }

    class NoteListKey() : StateKey, Parcelable {
        constructor(parcel: Parcel) : this()

        override fun layout(): Int = R.layout.note_list_screen
        override fun viewChangeHandler(): ViewChangeHandler = NoOpViewChangeHandler()

        override fun writeToParcel(parcel: Parcel, flags: Int) {}

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<NoteListKey> {
            override fun createFromParcel(parcel: Parcel): NoteListKey {
                return NoteListKey(parcel)
            }

            override fun newArray(size: Int): Array<NoteListKey?> {
                return arrayOfNulls(size)
            }
        }
    }
}
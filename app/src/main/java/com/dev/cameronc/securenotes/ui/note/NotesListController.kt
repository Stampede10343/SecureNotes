package com.dev.cameronc.securenotes.ui.note

import com.airbnb.epoxy.EpoxyController
import com.dev.cameronc.securenotes.data.BulletItem
import com.dev.cameronc.securenotes.data.BulletedNote
import com.dev.cameronc.securenotes.data.Note

class NotesListController(private val clickListener: NoteClickListener) : EpoxyController() {
    private val notes: MutableList<Note> = emptyList<Note>().toMutableList()
    private val bulletedNotes: MutableList<BulletedNote> = listOf(
            BulletedNote("Yo", listOf(BulletItem(true, "That", 1), BulletItem(false, "Do this", 0)), 1, 1)).toMutableList()

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteModel {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item_view, parent, false)
        return NoteModel()
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteModel, position: Int) {
        val note: Note = notes[position]

        *//*holder.title.text = note.title
        holder.content.text = note.content

        holder.itemView.setOnClickListener {
            clickListener.onNoteClicked(note)
        }*//*
    }*/

    override fun buildModels() {
        notes.forEach { note ->
            NoteModel_().id(note.id)
                    .title(note.title)
                    .content(note.content)
                    .clickListener { clickListener.onNoteClicked(note) }
                    .addTo(this)
        }

        bulletedNotes.forEach { bulletedNote ->
            BulletNoteModel_().id(bulletedNote.id)
                    .bulletItems(bulletedNote.bullets)
//                    .clickListener { clickListener.onNoteClicked(bulletedNote) }
                    .addTo(this)
        }
    }

    fun setNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)

        requestModelBuild()
    }

    interface NoteClickListener {
        fun onNoteClicked(note: Note)
    }
}
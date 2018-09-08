package com.dev.cameronc.securenotes.ui.note

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.SecureNotesApplication
import com.dev.cameronc.securenotes.data.BulletItem
import com.dev.cameronc.securenotes.ui.note.bulletlist.BulletRecyclerView

abstract class BaseNoteModel<T : BaseNoteModel.BaseNoteHolder> : EpoxyModelWithHolder<T>() {
    @EpoxyAttribute
    lateinit var title: String
    @EpoxyAttribute(hash = false)
    lateinit var clickListener: (id: Long) -> Unit

    override fun bind(holder: T) {
        super.bind(holder)
        holder.itemView.setOnClickListener { clickListener(id()) }
    }

    open class BaseNoteHolder : EpoxyHolder() {
        lateinit var itemView: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
        }
    }
}

@EpoxyModelClass(layout = R.layout.note_item_view)
abstract class NoteModel : BaseNoteModel<NoteModel.Holder>() {

    @EpoxyAttribute
    lateinit var content: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.title.text = title
        holder.content.text = content
    }

    class Holder : BaseNoteHolder() {
        lateinit var title: TextView
        lateinit var content: TextView

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            this.title = itemView.findViewById(R.id.note_item_title)
            this.content = itemView.findViewById(R.id.note_item_content)
        }
    }
}

@EpoxyModelClass(layout = R.layout.bullet_list_item)
abstract class BulletNoteModel : BaseNoteModel<BulletNoteModel.Holder>() {

    @EpoxyAttribute
    lateinit var bulletItems: List<BulletItem>

    override fun bind(holder: Holder) {
        super.bind(holder)
        val bulletListAdapter = BulletRecyclerView.BulletListAdapter()
        bulletListAdapter.setItems(bulletItems)
        holder.bulletList.adapter = bulletListAdapter
    }

    class Holder : BaseNoteHolder() {
        lateinit var bulletList: RecyclerView

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            this.bulletList = itemView.findViewById(R.id.note_detail_bullet_list)
            this.bulletList.setHasFixedSize(true)
            this.bulletList.layoutManager = LinearLayoutManager(SecureNotesApplication.context, LinearLayoutManager.VERTICAL, false)
        }
    }
}
package com.dev.cameronc.securenotes.ui.note.bulletlist

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.SecureNotesApplication
import com.dev.cameronc.securenotes.data.BulletItem

import javax.inject.Inject

class BulletRecyclerView(context: Context, attributeSet: AttributeSet, styleRes: Int) : RecyclerView(context, attributeSet, styleRes) {

    @Inject
    lateinit var viewModel: BulletListViewModel
    private val bulletAdapter: BulletListAdapter = BulletListAdapter()

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    init {
        if (!isInEditMode) {
            SecureNotesApplication.activityComponent.inject(this)

            layoutManager = LinearLayoutManager(context)
            adapter = bulletAdapter

            viewModel.listItems()
                    .subscribe { bulletItems -> bulletAdapter.setItems(bulletItems) }
        }
    }

    class BulletListAdapter : RecyclerView.Adapter<BulletListViewHolder>() {

        private val items: MutableList<BulletItem> = emptyList<BulletItem>().toMutableList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletListViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.bullet_item, parent, false)
            return BulletListViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BulletListViewHolder, position: Int) {
            val bulletItem = items[position]
            holder.checkBox.isChecked = bulletItem.isChecked
            holder.editText.setText(bulletItem.text)
        }

        override fun getItemCount(): Int = items.size

        fun setItems(newItems: List<BulletItem>) {
            val diffCallback = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = items.size
                override fun getNewListSize(): Int = newItems.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = newItems[newItemPosition].id == items[oldItemPosition].id
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = newItems[newItemPosition] == items[oldItemPosition]
            })

            items.clear()
            items.addAll(newItems)
            diffCallback.dispatchUpdatesTo(this)
        }
    }

    class BulletListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.bullet_item_checkbox)
        val editText: EditText = view.findViewById(R.id.bullet_item_text)
    }
}
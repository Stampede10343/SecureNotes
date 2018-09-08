package com.dev.cameronc.securenotes.ui

import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import javax.inject.Inject

class ToolbarWrapper @Inject constructor(private val appCompatActivity: AppCompatActivity, private val toolbar: Toolbar) {

    fun setText(title: String) {
        toolbar.title = title
    }

    fun addMenuItem(title: String, clickListener: () -> Boolean) {
        toolbar.menu.add(title)
                .setOnMenuItemClickListener { clickListener.invoke() }
    }

    fun addMenuIcon(title: String, @DrawableRes iconRes: Int, clickListener: () -> Boolean) {
        toolbar.menu.add(title)
                .setIcon(iconRes)
                .setOnMenuItemClickListener { clickListener.invoke() }
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    fun clearMenu() {
        toolbar.menu.clear()
    }

}
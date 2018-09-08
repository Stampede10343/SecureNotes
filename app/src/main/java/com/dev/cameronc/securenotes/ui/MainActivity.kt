package com.dev.cameronc.securenotes.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.SecureNotesApplication
import com.dev.cameronc.securenotes.di.DaggerActivityComponent
import com.dev.cameronc.securenotes.ui.note.NoteListScreen
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var toolbarWrapper: ToolbarWrapper
    lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        SecureNotesApplication.activityComponent = DaggerActivityComponent.builder()
                .appComponent(SecureNotesApplication.applicationComponent)
                .activityContext(this)
                .build()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SecureNotesApplication.activityComponent.inject(this)

        Navigator.install(this, findViewById(R.id.main_content), History.single(NoteListScreen.NoteListKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }
}

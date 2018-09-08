package com.dev.cameronc.securenotes.di

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.dev.cameronc.securenotes.R
import com.dev.cameronc.securenotes.ui.MainActivity
import com.dev.cameronc.securenotes.ui.note.NoteListScreen
import com.dev.cameronc.securenotes.ui.note.bulletlist.BulletRecyclerView
import com.dev.cameronc.securenotes.ui.note.detail.NoteDetailScreen
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@ActivityComponent.ActivityScope
@Component(modules = [(ActivityComponent.ActivityModule::class)], dependencies = [ApplicationComponent::class])
interface ActivityComponent {
    fun inject(noteListScreen: NoteListScreen)
    fun inject(noteListScreen: NoteDetailScreen)
    fun inject(mainActivity: MainActivity)
    fun inject(bulletRecyclerView: BulletRecyclerView)

    @Component.Builder
    interface ActivityComponentBuilder {
        fun appComponent(applicationComponent: ApplicationComponent): ActivityComponentBuilder
        @BindsInstance
        fun activityContext(activity: MainActivity): ActivityComponentBuilder

        fun build(): ActivityComponent
    }

    @Module
    class ActivityModule {

        @Provides
        fun activity(mainActivity: MainActivity): AppCompatActivity {
            return mainActivity
        }

        @Provides
        fun toolbar(activity: AppCompatActivity): Toolbar {
            return activity.findViewById(R.id.app_toolbar)
        }

        @Provides
        fun activityOptionsMenu(mainActivity: MainActivity): Menu = mainActivity.menu
    }

    @Scope
    annotation class ActivityScope
}
package com.dev.cameronc.securenotes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.dev.cameronc.securenotes.di.*
import timber.log.Timber

class SecureNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .build()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
        lateinit var activityComponent: ActivityComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}
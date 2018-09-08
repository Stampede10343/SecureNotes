package com.dev.cameronc.securenotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class AppModule(private val context: Context) {
    @Provides
    @AppContext
    fun appContext(): Context {
        return context.applicationContext
    }

    @Provides
    @UiThreadScheduler
    fun uiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @DatabaseScheduler
    fun ioScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
package com.dev.cameronc.securenotes

import com.dev.cameronc.securenotes.di.DatabaseScheduler
import com.dev.cameronc.securenotes.di.UiThreadScheduler
import io.reactivex.Scheduler
import javax.inject.Inject

class Schedulers @Inject constructor(@UiThreadScheduler val uiScheduler: Scheduler, @DatabaseScheduler val databaseScheduler: Scheduler)
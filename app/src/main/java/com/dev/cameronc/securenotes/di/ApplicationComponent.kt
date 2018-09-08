package com.dev.cameronc.securenotes.di

import com.dev.cameronc.securenotes.Schedulers
import com.dev.cameronc.securenotes.data.NoteDataProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface ApplicationComponent {
    fun noteDataProvider(): NoteDataProvider
    fun schedulers(): Schedulers
}
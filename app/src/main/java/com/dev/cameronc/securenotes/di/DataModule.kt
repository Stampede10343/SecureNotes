package com.dev.cameronc.securenotes.di

import android.arch.persistence.room.Room
import android.content.Context
import com.dev.cameronc.securenotes.data.NoteDataProvider
import com.dev.cameronc.securenotes.data.realm.RealmNotesDataProvider
import com.dev.cameronc.securenotes.data.room.NotesDao
import com.dev.cameronc.securenotes.data.room.RoomNotesDatabase
import dagger.Module
import dagger.Provides
import io.realm.Realm
import timber.log.Timber
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    fun notesDataProvider(notesDataProvider: RealmNotesDataProvider): NoteDataProvider {
        return notesDataProvider
    }

    @Provides
    fun roomNotesDao(roomNotesDatabase: RoomNotesDatabase): NotesDao {
        return roomNotesDatabase.notesDao()
    }

    @Provides
    fun provideRealm(@AppContext context: Context): Realm {
        Realm.init(context)
        Timber.d("Current Thread: %s", Thread.currentThread().name)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun notesRoomDatabase(@AppContext context: Context): RoomNotesDatabase {
        return Room.databaseBuilder(context, RoomNotesDatabase::class.java, "notes-database")
                .build()
    }
}
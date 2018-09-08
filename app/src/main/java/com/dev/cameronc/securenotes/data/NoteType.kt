package com.dev.cameronc.securenotes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class NoteType : Parcelable {
    Note, BulletNote
}
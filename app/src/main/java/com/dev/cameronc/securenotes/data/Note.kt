package com.dev.cameronc.securenotes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val id: Long = 0, val title: String, val content: String, val createDate: Long) : Parcelable
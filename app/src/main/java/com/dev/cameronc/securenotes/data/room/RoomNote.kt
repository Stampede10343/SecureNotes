package com.dev.cameronc.securenotes.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class RoomNote(@PrimaryKey(autoGenerate = true) val id: Long = 0, val title: String, val content: String, val createDate: Long) : Parcelable
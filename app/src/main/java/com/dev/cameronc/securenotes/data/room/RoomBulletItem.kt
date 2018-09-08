package com.dev.cameronc.securenotes.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RoomBulletItem(var isChecked: Boolean, var text: String, @PrimaryKey var id: Long = 0)
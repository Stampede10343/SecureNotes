package com.dev.cameronc.securenotes.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class RoomBulletedNote {
    lateinit var title: String
    //@Relation(parentColumn = "id", entityColumn = "id", entity = RoomBulletItem::class)
//    lateinit var bullets: List<RoomBulletItem>
    var createDate: Long = 0
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
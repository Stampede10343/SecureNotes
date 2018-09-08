package com.dev.cameronc.securenotes.data.realm

import io.realm.RealmList
import io.realm.RealmObject

open class RealmBulletedNote() : RealmObject() {
    var id: Long = 0
    var title: String = ""
    var bulletItems: RealmList<RealmBulletItem> = RealmList()
    var createDate: Long = 0

    constructor(id: Long, title: String, createDate: Long, bulletItems: RealmList<RealmBulletItem>) : this() {
        this.id = id
        this.title = title
        this.createDate = createDate
        this.bulletItems = bulletItems
    }
}
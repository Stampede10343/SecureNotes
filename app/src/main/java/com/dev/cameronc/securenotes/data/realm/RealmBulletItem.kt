package com.dev.cameronc.securenotes.data.realm

import io.realm.RealmObject

open class RealmBulletItem() : RealmObject() {
    var id: Long = 0
    var text: String = ""
    var isChecked: Boolean = false

    constructor(id: Long, text: String, isChecked: Boolean) : this() {
        this.id = id
        this.text = text
        this.isChecked = isChecked
    }
}
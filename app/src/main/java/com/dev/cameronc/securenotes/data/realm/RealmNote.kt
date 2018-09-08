package com.dev.cameronc.securenotes.data.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmNote : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var title: String = ""
    var content: String = ""
    var createDate: Long = 0
}
package com.dev.cameronc.securenotes.data

data class BulletedNote(var title: String, var bullets: List<BulletItem>, var createDate: Long = 0, var id: Long = 0)
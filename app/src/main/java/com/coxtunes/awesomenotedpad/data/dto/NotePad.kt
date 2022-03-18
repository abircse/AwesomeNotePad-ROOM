package com.coxtunes.awesomenotedpad.data.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class NotePad(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    val created_at: String,
    var description: String
): Parcelable
package com.turtlemint.assignment.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turtlemint.assignment.util.Constants
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = Constants.DB_CMTS_TABLE_NAME)
data class Comment(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val body: String,
    val created_at: String,
    val updated_at: String,
    val url: String,
    val user: User,
    var issueId: Int? = null
) : Parcelable
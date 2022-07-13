package com.turtlemint.assignment.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.turtlemint.assignment.util.Constants
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = Constants.DB_ISSUE_TABLE_NAME)
data class Issues(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val body: String?,
    val comments: Int,
    val comments_url: String,
    val created_at: String,
    val labels: List<Label>,
    val labels_url: String,
    val locked: Boolean,
    val node_id: String,
    val number: Int,
    val repository_url: String,
    val state: String,
    val timeline_url: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val user: User
) : Parcelable
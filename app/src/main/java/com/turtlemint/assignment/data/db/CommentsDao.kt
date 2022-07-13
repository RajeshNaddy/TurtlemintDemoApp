package com.turtlemint.assignment.data.db

import androidx.room.*
import com.turtlemint.assignment.data.model.Comment

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(commentList: List<Comment>)

    @Transaction
    @Query("SELECT * FROM comments WHERE issueId = :issueId")
    suspend fun getIssueComments(issueId: Int): List<Comment>
}
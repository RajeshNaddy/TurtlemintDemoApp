package com.turtlemint.assignment.data.repositories

import com.turtlemint.assignment.data.db.CommentsDao
import com.turtlemint.assignment.data.db.IssuesDao
import com.turtlemint.assignment.data.model.Comment
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.data.network.ApiInterface
import com.turtlemint.assignment.data.network.SafeApiRequest
import com.turtlemint.assignment.util.ApiException
import com.turtlemint.assignment.util.ConnectionTimedOutException
import com.turtlemint.assignment.util.DataState
import com.turtlemint.assignment.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MainRepository
@Inject
constructor(
    private val apiInterface: ApiInterface,
    private val issuesDao: IssuesDao,
    private val commentDao: CommentsDao
) : SafeApiRequest() {

    private var currentIssueList: List<Issues>? = null
    private var currentCommentList: List<Comment>? = null

    fun fetchAllIssues(): Flow<DataState<List<Issues>>> = flow {
        emit(DataState.Loading)
        if (currentIssueList != null) {
            emit(DataState.Success(currentIssueList!!))
        } else {
            val localCurrentIssueList = issuesDao.getAllIssues()
            if (localCurrentIssueList.isNotEmpty()) {
                currentIssueList = localCurrentIssueList
                emit(DataState.Success(currentIssueList!!))
            }
        }

        try {
            val networkIssueList = apiRequest {
                apiInterface.fetchIssues()
            }
            if (!networkIssueList.isNullOrEmpty()) {
                saveIssuesToDb(networkIssueList)
                currentIssueList = networkIssueList
                emit(DataState.Success(networkIssueList))
            }
        } catch (e: NoInternetException) {
            emit(DataState.Error(Exception("No Internet connection")))
        } catch (e: ApiException) {
            emit(DataState.Error(Exception("Api Exception")))
        } catch (e: ConnectionTimedOutException) {
            emit(DataState.Error(Exception("Connection timed out")))
        } catch (e: Exception) {
            emit(DataState.Error(Exception(e.localizedMessage)))
        }

    }.flowOn(Dispatchers.IO)


    fun fetchAllComments(commentUrl: String, issueId: Int): Flow<DataState<List<Comment>>> = flow {
        emit(DataState.Loading)

        val localCurrentCommentList = commentDao.getIssueComments(issueId)

        currentCommentList = localCurrentCommentList
        emit(DataState.Success(currentCommentList!!))

        try {
            val networkCommentList = apiRequest {
                apiInterface.fetchComments(commentUrl)
            }
            if (!networkCommentList.isNullOrEmpty()) {
                networkCommentList.forEach { it.issueId = issueId }
                saveIssueCommentsToDb(networkCommentList)
                currentCommentList = networkCommentList
                emit(DataState.Success(networkCommentList))
            }
        } catch (e: NoInternetException) {
            emit(DataState.Error(Exception("No Internet connection")))
        } catch (e: ApiException) {
            emit(DataState.Error(Exception("Api Exception")))
        } catch (e: ConnectionTimedOutException) {
            emit(DataState.Error(Exception("Connection timed out")))
        } catch (e: Exception) {
            emit(DataState.Error(Exception(e.localizedMessage)))
        }

    }.flowOn(Dispatchers.IO)

    private suspend fun saveIssuesToDb(issueList: List<Issues>) {
        issuesDao.saveAllIssues(issueList)
    }

    private suspend fun saveIssueCommentsToDb(commentList: List<Comment>) {
        commentDao.insertComments(commentList)
    }


}
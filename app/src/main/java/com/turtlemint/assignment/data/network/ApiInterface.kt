package com.turtlemint.assignment.data.network

import com.turtlemint.assignment.data.model.Comment
import com.turtlemint.assignment.data.model.Issues
import com.turtlemint.assignment.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET(Constants.ISSUES)
    suspend fun fetchIssues(): Response<List<Issues>>

    @GET
    suspend fun fetchComments(@Url commentUrl: String): Response<List<Comment>>
}
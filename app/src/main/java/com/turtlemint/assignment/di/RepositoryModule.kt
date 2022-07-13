package com.turtlemint.assignment.di

import com.turtlemint.assignment.data.db.CommentsDao
import com.turtlemint.assignment.data.db.IssuesDao
import com.turtlemint.assignment.data.repositories.MainRepository
import com.turtlemint.assignment.data.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMainRepository(
        apiInterface: ApiInterface,
        issuesDao: IssuesDao,
        commentsDao: CommentsDao
    ): MainRepository =
        MainRepository(apiInterface, issuesDao,commentsDao)

}
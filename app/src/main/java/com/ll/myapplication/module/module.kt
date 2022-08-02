package com.ll.myapplication.module

import android.content.Context
import androidx.room.Room
import com.ll.myapplication.data.datasource.ExamplePagingSource
import com.ll.myapplication.data.datasource.ExampleRemoteMediator
import com.ll.myapplication.data.datasource.FakeDataSource
import com.ll.myapplication.data.db.UserRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: ll
 * @CreateTime: 2022/08/02 15:59
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesUserDatabase(
        @ApplicationContext context: Context,
    ): UserRoom = Room.databaseBuilder(
        context,
        UserRoom::class.java,
        "user-database"
    ).build()
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesDataSource(
        @ApplicationContext context: Context,
    ): FakeDataSource = FakeDataSource(context)
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesExamplePagingSource(
        fakeDataSource: FakeDataSource
    ): ExamplePagingSource = ExamplePagingSource(fakeDataSource)


    @Provides
    @Singleton
    fun providesExampleRemoteMediator(
        fakeDataSource: FakeDataSource,
        db:UserRoom
    ): ExampleRemoteMediator = ExampleRemoteMediator(fakeDataSource,db)
}
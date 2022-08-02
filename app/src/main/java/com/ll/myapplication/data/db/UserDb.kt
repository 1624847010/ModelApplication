package com.ll.myapplication.data.db

import androidx.paging.PagingSource
import androidx.room.*

/**
 * @Author: ll
 * @CreateTime: 2022/08/01 17:35
 */
@Entity
data class Users(@PrimaryKey(autoGenerate = true) var id: Int = 0, val label: String)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Users>)

    @Query("SELECT * FROM users")
    fun pagingSource(): PagingSource<Int, Users>

    @Query("DELETE FROM users")
    suspend fun clearAll()
}

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UserRoom : RoomDatabase() {
    abstract fun dao(): UserDao
}

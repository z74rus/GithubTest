package com.example.githubmvp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.githubconductor.models.DetailUser
@Dao
interface UserDao {

    @Query(value = "SELECT * FROM DetailUser WHERE id=(:id)")
    suspend fun getDetailUserById(id: Long): DetailUser?

    @Insert(onConflict = REPLACE)
    suspend fun loadDetailUser(user: DetailUser)
    @Update
    suspend fun updateDetailUser(user: DetailUser)
    @Query("SELECT * FROM DetailUser")
    suspend fun getUsers(): List<DetailUser>
}
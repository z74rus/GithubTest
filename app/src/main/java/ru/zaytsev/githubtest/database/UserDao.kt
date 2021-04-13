package ru.zaytsev.githubtest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import ru.zaytsev.githubtest.models.DetailUser
@Dao
interface UserDao {
    @Query(value = "SELECT * FROM DetailUser WHERE id=(:id)")
    fun getDetailUser(id: Long): LiveData<DetailUser?>
    @Insert(onConflict = REPLACE)
    fun loadDetailUser(user: DetailUser?)
    @Update
    fun updateDetailUser(user: DetailUser?)
    @Query("SELECT * FROM DetailUser")
    fun getUsers(): LiveData<List<DetailUser>?>
}
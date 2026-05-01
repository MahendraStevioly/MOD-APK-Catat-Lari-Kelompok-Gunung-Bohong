package com.upn.catatlari.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.upn.catatlari.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}

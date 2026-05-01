package com.upn.catatlari.repository

import com.upn.catatlari.data.UserDao
import com.upn.catatlari.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}

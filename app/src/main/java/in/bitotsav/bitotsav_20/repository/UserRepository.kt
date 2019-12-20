package `in`.bitotsav.bitotsav_20.repository

import `in`.bitotsav.bitotsav_20.dao.UserDao
import `in`.bitotsav.bitotsav_20.entity.User
import androidx.annotation.WorkerThread

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insertUser(user: User) = userDao.insert(user)

    @WorkerThread
    suspend fun updateUser(user: User) = userDao.update(user)

    @WorkerThread
    suspend fun deleteUser(user: User) = userDao.delete(user)

    @WorkerThread
    suspend fun getUser() = userDao.getUser()
}
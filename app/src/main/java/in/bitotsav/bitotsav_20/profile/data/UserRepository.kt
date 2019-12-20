package `in`.bitotsav.bitotsav_20.profile.data

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
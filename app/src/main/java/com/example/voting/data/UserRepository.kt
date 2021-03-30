package com.example.voting.data

import android.content.Context
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    var loginDatabase: UserDatabase? = null
    var loginTableModel: LiveData<User>? = null
    val readAllData: LiveData<List<Voters>> = userDao.readAllData()

    suspend fun adduser(user: User) {
        userDao.addUser(user)
    }

    suspend fun addVoter(voters: Voters){
        userDao.addVoter(voters)
    }

    fun initializeDB(context: Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }


    fun getLoginDetails(context: Context, username: String, password: String): LiveData<User>? {

        loginDatabase = initializeDB(context)
        loginTableModel = loginDatabase!!.userDao().getLoginDetails(username,password)

        return loginTableModel
    }

}



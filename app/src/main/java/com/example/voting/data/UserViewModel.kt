package com.example.voting.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.voting.data.entities.User
import com.example.voting.data.entities.Voters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {


    val username_: MutableLiveData<String> = savedStateHandle.getLiveData("username", "")
    val passWord_: MutableLiveData<String> = savedStateHandle.getLiveData("password", "")

    val repository: UserRepository
    var liveDataLogin: LiveData<User>? = null
    val readAllData: LiveData<List<Voters>>

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.adduser(user)
        }
    }

    fun addVoters(voters: Voters) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVoter(voters)
        }
    }

    fun updateVoter(voters: Voters) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVoter(voters)
        }
    }


    fun getLoginDetails(context: Context, username: String, password: String): LiveData<User>? {
        liveDataLogin = repository.getLoginDetails(context, username, password)
        return liveDataLogin
    }

}

package com.example.passwordmanagerapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanagerapp.models.Account
import com.example.passwordmanagerapp.repository.AccountRepo
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    // instance of repository of account
    val repo : AccountRepo = AccountRepo()

    val accountList = MutableLiveData<List<Account>>()

    fun insert(context : Context,account: Account){
        viewModelScope.launch {
            repo.insert(context,account)
        }
    }

    fun delete(context : Context,account: Account){
        viewModelScope.launch {
            repo.delete(context, account)
        }
    }

    fun update(context : Context,account: Account){
        viewModelScope.launch {
            repo.update(context, account)
        }
    }

    fun getAllAccounts(context : Context){
        viewModelScope.launch {
            accountList.value = repo.getAllAccounts(context)
        }
    }

}
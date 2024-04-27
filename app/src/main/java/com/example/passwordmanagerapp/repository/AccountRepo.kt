package com.example.passwordmanagerapp.repository

import android.content.Context
import com.example.passwordmanagerapp.models.Account
import com.example.passwordmanagerapp.room.AccountDatabase

class AccountRepo {

    suspend fun insert(context : Context, account : Account){
        AccountDatabase.get(context).getAccountDAO().insert(account)
    }

    suspend fun delete(context : Context , account: Account){
        AccountDatabase.get(context).getAccountDAO().delete(account)
    }

    suspend fun update(context : Context , account: Account){
        AccountDatabase.get(context).getAccountDAO().update(account)
    }

    suspend fun getAllAccounts(context : Context) : List<Account>{
        return AccountDatabase.get(context).getAccountDAO().getAllAccounts()
    }

}
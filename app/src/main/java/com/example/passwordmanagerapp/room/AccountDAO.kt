package com.example.passwordmanagerapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanagerapp.models.Account

@Dao
interface AccountDAO {

    @Insert
    suspend fun insert(newAccount : Account)

    @Delete
    suspend fun delete(currAccount : Account)

    @Update
    suspend fun update(currAccount: Account)

    @Query("SELECT * FROM ACCOUNT")
    suspend fun getAllAccounts() : List<Account>
}
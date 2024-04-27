package com.example.passwordmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordmanagerapp.models.Account

@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun getAccountDAO() : AccountDAO

    companion object{
        fun get(context : Context) : AccountDatabase{
            return Room.databaseBuilder(context,AccountDatabase::class.java,"account_info").build()
        }
    }
}
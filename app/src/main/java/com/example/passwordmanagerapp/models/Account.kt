package com.example.passwordmanagerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
class Account(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val accountName : String,
    val userNameOrEmail : String,
    val encryptedPassword : String
) : Parcelable {
}
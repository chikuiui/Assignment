package com.example.passwordmanagerapp.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanagerapp.R


class SplashActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // for icons on status bar to be black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // hiding the action bar from splash screen
        supportActionBar?.hide()


        Handler(Looper.myLooper()!!).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
            }, 3000
        )



    }





}
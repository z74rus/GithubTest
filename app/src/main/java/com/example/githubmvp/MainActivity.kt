package com.example.githubmvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubmvp.fragments.MainFragment
import com.example.githubmvp.fragments.StartFragment
import moxy.MvpAppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            val fragmentManager = supportFragmentManager

            if(fragmentManager.fragments.size == 0) {
                fragmentManager.beginTransaction()
                    .add(R.id.parentContainer, StartFragment())
                    .commit()
            }
        }
    }
}
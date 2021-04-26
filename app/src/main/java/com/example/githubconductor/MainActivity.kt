package com.example.githubconductor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.controllers.StartController

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container: ViewGroup = findViewById(R.id.controller_container)

        router = Conductor.attachRouter(
                activity = this, container = container, savedInstanceState = savedInstanceState
        )
        if(!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(StartController()))
        }
    }

    override fun onBackPressed() {
        if(!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
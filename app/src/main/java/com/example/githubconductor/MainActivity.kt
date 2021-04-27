package com.example.githubconductor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.controllers.StartController
import com.example.githubconductor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        router = Conductor.attachRouter(
                activity = this, container = binding.controllerContainer, savedInstanceState = savedInstanceState
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
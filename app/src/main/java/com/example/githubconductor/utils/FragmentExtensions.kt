package com.example.githubconductor.utils

import android.os.Bundle
import moxy.MvpAppCompatFragment

fun <T: MvpAppCompatFragment> T.withArguments(action: Bundle.()-> Unit): T {
    return apply {
        arguments = Bundle().also(action)
    }
}
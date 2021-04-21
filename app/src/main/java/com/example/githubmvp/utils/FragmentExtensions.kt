package com.example.githubmvp.utils

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import moxy.MvpAppCompatFragment

fun <T: MvpAppCompatFragment> T.withArguments(action: Bundle.()-> Unit): T {
    return apply {
        arguments = Bundle().also(action)
    }
}
package com.example.findtaste.models

import android.content.Context
import android.widget.Toast

class Tools {
    companion object{
        fun String.toast(c: Context) {
            Toast.makeText(c, this, Toast.LENGTH_LONG).show()
        }
    }
}
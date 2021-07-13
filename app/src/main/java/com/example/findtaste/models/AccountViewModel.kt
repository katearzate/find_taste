package com.example.findtaste.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel: ViewModel() {
    private var mail = MutableLiveData<String>()
    val mailLog: LiveData<String> get() = mail

    fun setEmail(email: String) {
        mail.value = email
    }
}
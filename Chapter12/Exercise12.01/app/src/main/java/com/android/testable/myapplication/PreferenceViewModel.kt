package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PreferenceViewModel(private val preferenceWrapper: PreferenceWrapper) : ViewModel() {

    fun saveText(text: String) {
        preferenceWrapper.saveText(text)
    }

    fun getText(): LiveData<String> {
        return preferenceWrapper.getText()
    }
}
package com.example.booksharing.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.room.AppDatabase
import kotlinx.coroutines.launch

class AppViewModel(db: AppDatabase): ViewModel() {
    var userName = ""

    // room からユーザー名を取得する
    init {
        viewModelScope.launch {
            userName = db.userDataDao().getUserData().userName ?: ""
        }
    }
}
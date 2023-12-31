package com.example.booksharing.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.testData.TestBooksData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    // test data TODO: replace with actual data
    private var _booksList = MutableStateFlow<List<TestBooksData>>(emptyList())
    val booksList = _booksList.asStateFlow()
    fun getBooksTest() {
        // booksList の初期化
        _booksList.value = emptyList()

        viewModelScope.launch {
            for (i in 0..20) {
                _booksList.value += TestBooksData()
                // データの取得までのタイムラグを再現
                delay(200)
            }
        }
    }


}
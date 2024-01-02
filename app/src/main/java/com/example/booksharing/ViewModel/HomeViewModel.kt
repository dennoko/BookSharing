package com.example.booksharing.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.firestore.ManageData
import com.example.booksharing.firestore.detailforapi
import com.example.booksharing.testData.TestBooksData
import com.google.common.collect.ImmutableList
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    // Firestore
    val db = Firebase.firestore
    // Repository
    val managedata = ManageData()

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

    // 本を取得する関数. 取得したデータはListで返す.
    suspend fun getBooks(tag: String): ImmutableList<detailforapi> {
        val booksData = viewModelScope.async { managedata.getbookbytag(db, tag) }.await()
        return booksData
    }
}
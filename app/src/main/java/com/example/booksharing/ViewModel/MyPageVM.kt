package com.example.booksharing.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.GoogleBooksAPI.BooksData
import com.example.booksharing.firestore.ManageData
import com.example.booksharing.firestore.detailforapi
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageVM: ViewModel() {
    // Instance of ManageData
    private val manageData = ManageData()
    private val db = Firebase.firestore

    // 書籍の検索キーワード
    var keyWord = mutableStateOf("")

    // 自分の書籍のリストを取得する関数
    var _myBooksList = MutableStateFlow<ImmutableList<detailforapi>?> (null)
    val myBooksList = _myBooksList.asStateFlow()

    fun getMyBooks() {
        viewModelScope.launch {
            // Todo: 持ち主のアカウント名が入るようにする
            _myBooksList.value = manageData.getBooksByOwner(db, "owner")
        }
    }

    // 追加する書籍を検索する関数
    var _searchedBooksData = MutableStateFlow<BooksData?> (null)
    val searchedBooksData = _searchedBooksData.asStateFlow()

    fun searchBooks() {
        viewModelScope.launch {
            _searchedBooksData.value = manageData.searchBooks(keyWord.value)
        }
    }

    // 書籍を追加する関数（タグ関係の実装が）
}
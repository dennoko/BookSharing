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

class MyBooksViewModel: ViewModel() {
    // Instance of ManageData
    val manageData = ManageData()
    val db = Firebase.firestore

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

    // タグのリストを取得する関数
    var _tagsList = MutableStateFlow<ImmutableList<String>?> (null)
    val tagsList = _tagsList.asStateFlow()

    fun getTags() {
        viewModelScope.launch {
            _tagsList.value = manageData.getTagsList(db)
        }
    }

    // 書籍を追加する関数. 選択された書籍の情報＋タグを引数に取る.
    fun addBook(bookInfo: detailforapi, tag1:String = "", tag2:String = "", tag3:String = "", tag4:String = "", tag5:String = "") {
        manageData.registbook(bookInfo.detail.owner, bookInfo.detail.isbn, tag1, tag2, tag3, tag4, tag5, db)
    }
}
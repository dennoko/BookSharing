package com.example.booksharing.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.repository.ManageData
import com.example.booksharing.firestore.detailforapi
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.testData.TestBooksData
import com.google.common.collect.ImmutableList
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    // Firestore
    private val db = Firebase.firestore
    // Repository
    private val manageData = ManageData()

    // 書籍の詳細を表示するかを管理する変数
    var isShowBookDetail = mutableStateOf(false)

    // 書籍の詳細を表示する書籍の情報を格納する変数
    var selectedBookInfo = mutableStateOf<detailforapi?>(null)

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
        val booksData = viewModelScope.async { manageData.getBooksByTag(db, tag) }.await()
        return booksData
    }

    // 登録済みのユーザーのリストを取得する関数(初回起動時に使用)
    var _usersList = MutableStateFlow<ImmutableList<String>?>(null)
    val usersList = _usersList.asStateFlow()
    fun getUsersList() {
        viewModelScope.launch {
            _usersList.value = manageData.getOwnerList(db)
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

    // 本を予約する関数
    fun registBrowwer(owner: String, isbn: String, borrower: String) {
        viewModelScope.launch {
            manageData.registBrrower(db, owner, isbn, borrower)
        }
    }
}
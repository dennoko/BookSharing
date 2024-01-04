package com.example.booksharing.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksharing.GoogleBooksAPI.BooksData
import com.example.booksharing.repository.ManageData
import com.example.booksharing.firestore.detailforapi
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyBooksViewModel: ViewModel() {
    // ユーザー名
    var owner = ""

    // Instance of ManageData
    private val manageData = ManageData()
    private val db = Firebase.firestore

    // 書籍の検索キーワード
    var keyWord = mutableStateOf("")

    // ダイアログの表示状態
    var isShowDialog = mutableStateOf(false)
    // タグのStringを格納する変数
    var tag1 = mutableStateOf("")
    var tag2 = mutableStateOf("")
    var tag3 = mutableStateOf("")
    var tag4 = mutableStateOf("")
    var tag5 = mutableStateOf("")

    // 選択されたタグをtag1~5に順に格納する関数
    fun setTag(tag: String) {
        if(tag1.value == "") {
            tag1.value = tag
        } else if(tag2.value == "") {
            tag2.value = tag
        } else if(tag3.value == "") {
            tag3.value = tag
        } else if(tag4.value == "") {
            tag4.value = tag
        } else if(tag5.value == "") {
            tag5.value = tag
        } else {
            // Todo: タグがこれ以上追加できないことをユーザーに伝える
        }
    }

    // 選択解除されたタグをtag1~5から削除する関数
    fun removeTag(tag: String) {
        if(tag1.value == tag) {
            tag1.value = ""
        } else if(tag2.value == tag) {
            tag2.value = ""
        } else if(tag3.value == tag) {
            tag3.value = ""
        } else if(tag4.value == tag) {
            tag4.value = ""
        } else if(tag5.value == tag) {
            tag5.value = ""
        } else {

        }
    }

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

            keyWord.value = ""
        }
    }

    // タグのリストを取得する関数
    var _tagsList = MutableStateFlow<List<String>?> (null)
    val tagsList = _tagsList.asStateFlow()

    fun getTags() {
        viewModelScope.launch {
            _tagsList.value = manageData.getTagsList(db)
        }
    }

    // タグを追加する関数
    var newTag = mutableStateOf("")
    fun addNewTag() {
        if(newTag.value != "") {
            // 既存のタグと重複していないか確認する
            if(_tagsList.value?.contains(newTag.value) == false) {
                // 重複していない場合は_tagsListにタグを追加する
                _tagsList.value = _tagsList.value?.plus(newTag.value)

                // タグを追加した後は、タグを空にする
                newTag.value = ""
            }
        }
    }


    // 書籍を追加する関数. 選択された書籍の情報＋タグを引数に取る.
    fun addBook(bookInfo: detailforapi) {
        manageData.registBook(bookInfo.detail.owner, bookInfo.detail.isbn, tag1.value, tag2.value, tag3.value, tag4.value, tag5.value, db)
    }
}
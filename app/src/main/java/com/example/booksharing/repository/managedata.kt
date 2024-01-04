package com.example.booksharing.repository
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.booksharing.GoogleBooksAPI.BooksData
import com.example.booksharing.GoogleBooksAPI.RetrofitInstance
import com.example.booksharing.firestore.detaildata
import com.example.booksharing.firestore.detailforapi
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import java.lang.Exception

class ManageData {
    //本を追加
    fun registBook(owner:String,isbn:String, tag1:String = "", tag2:String = "", tag3:String = "", tag4:String = "", tag5:String = "", db:FirebaseFirestore){
        val book= hashMapOf(
            "isbooked" to false,
            "owner" to owner,
            "borrower" to "",
            "isbn" to isbn,
            "tag1" to tag1,
            "tag2" to tag2,
            "tag3" to tag3,
            "tag4" to tag4,
            "tag5" to tag5
        )

        db.collection("C0de").document(owner+isbn)
            //非同期処理に成功したときの処理
            .set(book).addOnSuccessListener {
                Log.d("methodTest","new bookData added isbn:${isbn}")
            }
            //非同期処理に失敗したときの処理
            .addOnFailureListener {
                e:Exception ->
                Log.w("methodTest","error adding bookData ${e.message} ${e.cause}")
            }
    }
    //データを取得(タグ検索利用)
    suspend fun getBooksByTag(db:FirebaseFirestore,tag:String): ImmutableList<detailforapi> {
        val bookList= mutableListOf<detailforapi>()
        var tmp:BooksData
        try{
            for (i in 1..5){
                val tagNum="tag${i}"
                val result = db.collection("C0de").whereEqualTo(tagNum, tag).get().await()
                for (document in result) {
                    tmp = searchBooks((document.data["isbn"]).toString())
                    bookList.add(
                        detailforapi(
                            detaildata(
                                isbooked = document.data["isbooked"] as Boolean,
                                owner = document.data["owner"] as String,
                                borrower = document.data["borrower"] as String,
                                isbn = document.data["isbn"] as String,
                                tag1 = document.data["tag1"] as String,
                                tag2 = document.data["tag2"] as String,
                                tag3 = document.data["tag3"] as String,
                                tag4 = document.data["tag4"] as String,
                                tag5 = document.data["tag5"] as String
                            ),
                            tmp.items[0]
                        )
                    )
                }
            }

        }catch(e:Exception){
            Log.d("error", "getBookByTag: error occurred  ${e.message}  ${e.cause}")
        }
        return ImmutableList.copyOf(bookList)
    }

    // owner で本を検索する関数
    suspend fun getBooksByOwner(db: FirebaseFirestore, owner: String): ImmutableList<detailforapi> {
        val booklist = mutableListOf<detailforapi>()
        var tmp: BooksData
        try {
            val result = db.collection("C0de").whereEqualTo("owner", owner).get().await()
            for (document in result) {
                tmp = searchBooks((document.data["isbn"]).toString())
                booklist.add(
                    detailforapi(
                        detaildata(
                            isbooked = document.data["isbooked"] as Boolean,
                            owner = document.data["owner"] as String,
                            borrower = document.data["borrower"] as String,
                            isbn = document.data["isbn"] as String,
                            tag1 = document.data["tag1"] as String,
                            tag2 = document.data["tag2"] as String,
                            tag3 = document.data["tag3"] as String,
                            tag4 = document.data["tag4"] as String,
                            tag5 = document.data["tag5"] as String
                        ),
                        tmp.items[0]
                    )
                )
            }
        } catch (e: Exception) {
            Log.d("error", "getbookbytag: error occured  ${e.message}  ${e.cause}")
        }

        return ImmutableList.copyOf(booklist)
    }

    //本を削除
    fun deleteBook(db:FirebaseFirestore, collection:String, document:String, detail: detaildata, context:Context){
        val bookRef=db.collection(collection).document(document)
        bookRef.delete()
            .addOnSuccessListener{
                Toast.makeText(context,"BookData deleted",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e:Exception ->
                Toast.makeText(context,"failed to delete",Toast.LENGTH_SHORT).show()
                Log.d("methodTest","deleteTag: error occurred ${e.message} ${e.cause}")
            }
    }

    // tag1 から tag5 までのタグの値の種類のリストを返す関数
    suspend fun getTagsList(db: FirebaseFirestore): ImmutableList<String> {
        val tagList = mutableListOf<String>()
        try {
            val result = db.collection("C0de").get().await()
            for (document in result) {
                for (i in 1..5) {
                    val tagNum = "tag${i}"
                    val tag = document.data[tagNum] as String
                    if (tag != "") {
                        tagList.add(tag)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("error", "getTagList: error occurred  ${e.message}  ${e.cause}")
        }
        // 重複を削除
        tagList.distinct()
        // ImmutableList に変換して返す
        return ImmutableList.copyOf(tagList)
    }

    // owner のリストを返す関数. 重複を削除して返す. 新しいownerの登録時に、既に登録されているownerのリストを取得するために使用する.
    suspend fun getOwnerList(db: FirebaseFirestore): ImmutableList<String> {
        val ownerList = mutableListOf<String>()
        try {
            val result = db.collection("C0de").get().await()
            for (document in result) {
                val owner = document.data["owner"] as String
                if (owner != "") {
                    ownerList.add(owner)
                }
            }
        } catch (e: Exception) {
            Log.d("error", "getOwnerList: error occurred  ${e.message}  ${e.cause}")
        }
        // 重複を削除
        ownerList.distinct()
        // ImmutableList に変換して返す
        return ImmutableList.copyOf(ownerList)
    }


    // 以下はGoogle Books API 用の関数
    // Retrofit のインスタンスを作成
    private val apiService = RetrofitInstance.apiService

    // 本の検索結果を返す関数
    suspend fun searchBooks(query: String): BooksData {
        return apiService.searchBooks(query)
    }
}
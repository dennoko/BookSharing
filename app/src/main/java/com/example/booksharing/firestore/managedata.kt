package com.example.booksharing.firestore
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.booksharing.GoogleBooksAPI.BooksData
import com.example.booksharing.GoogleBooksAPI.Item
import com.example.booksharing.GoogleBooksAPI.RetrofitInstance
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import java.lang.Exception

class ManageData {
    //本を追加
    fun registbook(owner:String,isbn:String, tag1:String = "", tag2:String = "", tag3:String = "", tag4:String = "", tag5:String = "", db:FirebaseFirestore){
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
                Log.d("methodtest","new bookdata added isbn:${isbn}")
            }
            //非同期処理に失敗したときの処理
            .addOnFailureListener {
                e:Exception ->
                Log.w("methodtest","error adding bookdata ${e.message} ${e.cause}")
            }
    }
    //データを取得(タグ検索利用)
    suspend fun getbookbytag(db:FirebaseFirestore,tag:String): ImmutableList<detailforapi> {
        val booklist= mutableListOf<detailforapi>()
        var tmp:BooksData
        try{
            for (i in 1..5){
                val tagNum="tag${i}"
                val result = db.collection("C0de").whereEqualTo(tagNum, tag).get().await()
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
            }

        }catch(e:Exception){
            Log.d("error", "getbookbytag: error occured  ${e.message}  ${e.cause}")
        }
        return ImmutableList.copyOf(booklist)
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
    fun deletebook(db:FirebaseFirestore,collection:String, document:String,detail:detaildata,context:Context){
        val bookRef=db.collection(collection).document(document)
        bookRef.delete()
            .addOnSuccessListener{
                Toast.makeText(context,"Bookdata deleted",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e:Exception ->
                Toast.makeText(context,"failed to delete",Toast.LENGTH_SHORT).show()
                Log.d("methodtest","deletetag: error occured ${e.message} ${e.cause}")
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
            Log.d("error", "getTagList: error occured  ${e.message}  ${e.cause}")
        }
        // 重複を削除
        tagList.distinct()
        // ImmutableList に変換して返す
        return ImmutableList.copyOf(tagList)
    }


    // 以下はGoogle Books API 用の関数
    // Retrofit のインスタンスを作成
    private val apiService = RetrofitInstance.apiService

    // 本の検索結果を返す関数
    suspend fun searchBooks(query: String): BooksData {
        return apiService.searchBooks(query)
    }
}
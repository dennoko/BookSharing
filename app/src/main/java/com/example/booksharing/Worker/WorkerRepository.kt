package com.example.booksharing.Worker

import android.content.Context
import com.example.booksharing.room.AppDatabase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class WorkerRepository(context: Context) {
    val db = Firebase.firestore
    val roomDB = AppDatabase.getDB(context)

    // Firestore から自分の本のISBNとbrrrowerを取得する関数
    suspend fun getMyBooks(): List<String> {
        val owner = roomDB.userDataDao().getUserData().userName
        val result = db.collection("C0de").whereEqualTo("owner", owner).get().await()
        val books = ArrayList<String>()
        for (document in result) {
            if(document.data["borrower"] != "") {
                books.add(document.data["isbn"].toString())
            }
        }
        return books
    }
}
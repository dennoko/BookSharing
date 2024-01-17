package com.example.booksharing.Worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.booksharing.room.AppDatabase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class WorkerRepository(context: Context) {
    val db = Firebase.firestore
    val roomDB = AppDatabase.getDB(context)

    // Firestore から自分の本のISBNとborrowerを取得する関数
    suspend fun getMyBooks(): List<String> {
        val owner = roomDB.userDataDao().getUserData().userName
        val result = db.collection("C0de").whereEqualTo("owner", owner).get().await()
        val booksInfo = ArrayList<String>()
        for (document in result) {
            if(document.data["borrower"] != "") {
                booksInfo.add("${document.data["owner"]} ${document.data["isbn"].toString()}")
            }
        }
        return booksInfo
    }

    // 通知を送る関数
    fun sendNotification(context: Context, content: String) {
        // get NotificationManager
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // チャンネルを作成
        val notificationChannel = NotificationChannel("bookSharingCh", "bookSharing", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(notificationChannel)

        // NotificationBuilderを作成
        val notification = android.app.Notification.Builder(context, "bookSharingCh")
            .setContentTitle("本が予約されています")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        // 通知を送る
        notificationManager.notify(1, notification)
    }
}
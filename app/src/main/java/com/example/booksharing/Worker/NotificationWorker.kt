package com.example.booksharing.Worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import kotlinx.coroutines.delay

class NotificationWorker(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(appContext, workerParameters) {
    val workerRepository = WorkerRepository(appContext)
    override suspend fun doWork(): Result {
        Log.d("notificationTest", "doWork")
        return try {
            val preDataJson = inputData.getString("preData")
            val preData = preDataJson?.let { json ->
                Gson().fromJson(json, Array<String>::class.java)
            }
            Log.d("methodTest", "前回取得したデータ：${preData}")
            var outputData: Data? = null
            delay(1500)

            if(preData != null) {
                val books = workerRepository.getMyBooks()
                Log.d("methodTest", "新しく取得したデータ：${books}")
                // booksを現在のデータとして保存
                val booksJson = Gson().toJson(books)
                outputData = workDataOf("preData" to booksJson)

                // preDataと比較して、追加された要素を取得
                val addedBooks = books.filter { !preData.contains(it) }
                Log.d("methodTest", "追加されたデータ：${addedBooks}")

                // 差分がある場合は通知を送る
                if(addedBooks.isNotEmpty()) {
                    // 通知を送る
                    workerRepository.sendNotification(applicationContext, "あなたの本が予約されました")
                    Log.d("notificationTest", "通知を送りました(1)")
                }
            } else {
                // 初回起動時は現在のデータを保存
                val books = workerRepository.getMyBooks()
                Log.d("methodTest", "初回起動：${books}")
                outputData = workDataOf("preData" to Gson().toJson(books))
                Log.d("methodTest", "保存したデータ：${outputData}")

                // 通知を送る
                workerRepository.sendNotification(applicationContext, "あなたの本が予約されています")
                Log.d("notificationTest", "通知を送りました(2)")
            }

            Result.success(outputData)
        } catch (e: Exception) {
            Log.d("notificationTest", "通知を送る処理でエラーが発生しました。 ${e.message} ${e.cause}")
            Result.failure()
        }
    }
}
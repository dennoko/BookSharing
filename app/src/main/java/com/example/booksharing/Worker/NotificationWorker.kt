package com.example.booksharing.Worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import kotlinx.coroutines.delay

class NotificationWorker(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(appContext, workerParameters) {
    val workerRepository = WorkerRepository(appContext)
    override suspend fun doWork(): Result {
        Log.d("notificationTest", "doWork")
        return try {
            val preDataJson = inputData.getString("preData")
            Log.d("notificationTest", "前回取得したデータのJSON：${preDataJson}")
            val preData = preDataJson?.let { json ->
                Log.d("notificationTest", "前回取得したデータ：${json}")
                Gson().fromJson(json, Array<String>::class.java)
            }
            Log.d("notificationTest", "前回取得したデータ：${preData}")
            var outputData: Data? = null
            delay(1500)

            if(preData != null) {
                val books = workerRepository.getMyBooks()
                Log.d("notificationTest", "新しく取得したデータ：${books}")
                // booksを現在のデータとして保存
                val booksJson = Gson().toJson(books)
                outputData = workDataOf("preData" to booksJson)

                // bookTitle
                val borrowers = books.map { it.split(" ")[0] }

                // preDataと比較して、追加された要素を取得
                val addedBooks = books.filter { !preData.contains(it) }
                Log.d("notificationTest", "追加されたデータ：${addedBooks}")

                // 差分がある場合は通知を送る
                if(addedBooks.isNotEmpty()) {
                    // 通知を送る
                    workerRepository.sendNotification(applicationContext, "予約者：${borrowers}")
                    Log.d("notificationTest", "通知を送りました(1)")
                }
            } else {
                // 初回起動時は現在のデータを保存
                val books = workerRepository.getMyBooks()
                Log.d("notificationTest", "初回起動：${books}")
                outputData = workDataOf("preData" to Gson().toJson(books))
                Log.d("notificationTest", "保存したデータ：${outputData}")

                // bookTitle
                val borrowers = books.map { it.split(" ")[0] }

                // 通知を送る
                workerRepository.sendNotification(applicationContext, "予約者：${borrowers}")
                Log.d("notificationTest", "通知を送りました(2)")
            }

            Result.success(outputData)
        } catch (e: Exception) {
            Log.d("notificationTest", "通知を送る処理でエラーが発生しました。 ${e.message} ${e.cause}")
            Result.failure()
        }
    }
}
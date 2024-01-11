package com.example.booksharing.Worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class NotificationWorker(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(appContext, workerParameters) {
    val db = Firebase.firestore
    val workerRepository = WorkerRepository(appContext)
    override suspend fun doWork(): Result {
        return try {
            val preData = inputData.getStringArray("preData")
            var outputData: Data? = null

            if(preData != null) {
                val books = workerRepository.getMyBooks()
                // booksを現在のデータとして保存
                outputData = workDataOf("preData" to books)

                // preDataとbooksの要素の差分を取得
                val diff = books.filter { !preData.contains(it) }

                // 差分がある場合は通知を送る
                if(diff.isNotEmpty()) {
                    // 通知を送る
                    workerRepository.sendNotification(applicationContext, "あなたの本が借りられました")
                }
            } else {
                // 初回起動時は現在のデータを保存
                val books = workerRepository.getMyBooks()
                outputData = workDataOf("preData" to books)

                // 通知を送る
                workerRepository.sendNotification(applicationContext, "あなたの本が借りられました")
            }

            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
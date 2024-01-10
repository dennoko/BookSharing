package com.example.booksharing.Worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationWorker(appContext: Context, workerParameters: WorkerParameters): Worker(appContext, workerParameters) {
    val db = Firebase.firestore
    val workerRepository = WorkerRepository(appContext)
    override fun doWork(): Result {
        val preData = inputData.getIntArray("preData")

        if(preData != null) {
            withContext(Dispatchers.IO) {
                
            }

            val books = workerRepository.getMyBooks()
        }

        return Result.success()
    }
}
package com.example.booksharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.booksharing.Worker.NotificationWorker
import com.example.booksharing.screen.HomeScreen
import com.example.booksharing.screen.MyBooksScreen
import com.example.booksharing.screen.SettingScreen
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.NavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WorkManagerの設定
        val workRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, java.util.concurrent.TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workRequest)

        setContent {
            val navController = rememberNavController()

            BookSharingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier
                                    .height(56.dp)
                            ) {
                                NavigationBar(navController)
                            }
                        }
                    ) {
                        NavHost(navController = navController, startDestination = "home" , modifier = Modifier.padding(it)) {
                            composable("home") {
                                HomeScreen(navController = navController)
                            }
                            composable("mypage") {
                                MyBooksScreen(navController = navController)
                            }
                            composable("setting") {
                                SettingScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
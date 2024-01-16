package com.example.booksharing.ui_components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigation

@Composable
fun NavigationBar(navController: NavController) {
    /*
    このコンポーザブルは、アプリの下部に表示されるナビゲーションバーです。
    このコンポーザブルは、画面遷移を行うためのコンポーザブルです。
     */
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        ) {
        NavigateTab(
            navController = navController,
            route = "home",
            txt = "ホーム",
            icon = Icons.Default.Home,
            onClick = { selectedTabIndex = 0 }
        )
        NavigateTab(
            navController = navController,
            route = "myPage",
            txt = "本を管理",
            icon = Icons.Default.Person,
            onClick = { selectedTabIndex = 1 }
        )
        NavigateTab(
            navController = navController,
            route = "setting",
            txt = "設定",
            icon = Icons.Default.Settings,
            onClick = { selectedTabIndex = 2 }
        )
    }
}

@Composable
fun NavigateTab(navController: NavController, route: String, txt: String, icon :ImageVector, onClick: () -> Unit) {
    Tab(
        selected =  false,
        onClick = { if (navController.currentDestination!!.route != route) {
            navController.navigate(route)
            onClick()
        } },
        text = { Text(text = txt) },
        icon = {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = icon,
                contentDescription = "アイコン"
            )
        }
    )
}
package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun libraryUI(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(24.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "検索アイコン",)
                Spacer(modifier = Modifier.padding(16.dp))
                TextField(value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "検索ワードを入力") })
            }
            Spacer(modifier = Modifier.width(12.dp))
            Row() {
                Text(text = "所有している本", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(24.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "本の追加")
                }
            }
            //ここでfirestoreから自分が所有する本の情報を取得し、並べるように修正する
        }
        Box {
            Button(onClick = {},modifier=Modifier.align(Alignment.BottomCenter)) {
                Text(text = "ホームに戻る")
            }
        }
    }
}
package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyBooksScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var showDialog by remember { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(24.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "検索アイコン",)
                Spacer(modifier = Modifier.padding(16.dp))
                TextField(value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "検索ワードを入力") })
            }
            Spacer(modifier = Modifier.width(12.dp))
            Row {
                Text(text = "所有している本", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(24.dp))
                Button(onClick = { showDialog=true }) {
                    Text(text = "本の追加")
                }
            }
        }
        //ここでfirestoreから自分が所有する本の情報を取得し、並べるようにする

        Box {
            Button(onClick = {/*画面遷移*/},modifier=Modifier.align(Alignment.BottomCenter)) {
                Text(text = "ホームに戻る")
            }
        }
        var isbn by remember { mutableStateOf("") }
        var tag1 by remember { mutableStateOf("") }
        var tag2 by remember { mutableStateOf("") }
        var tag3 by remember { mutableStateOf("") }
        var tag4 by remember { mutableStateOf("") }
        var tag5 by remember { mutableStateOf("") }
        if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { /*本を登録*/ }) {
                        Text(text = "追加")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog=false}) {
                        Text(text = "キャンセル")
                    }
                },
                title = {
                    Text(text = "本の追加")
                },
                text = {
                    Column {
                        Text("追加する本の情報を入力してください。")
                        Text(text = "ISBN")
                        TextField(value =isbn , onValueChange = {isbn=it})
                        Text(text = "1つめのタグ")
                        TextField(value =tag1 , onValueChange = {tag1=it})
                        Text(text = "2つめのタグ")
                        TextField(value =tag2 , onValueChange = {tag2=it})
                        Text(text = "3つめのタグ")
                        TextField(value =tag3 , onValueChange = {tag3=it})
                        Text(text = "4つめのタグ")
                        TextField(value =tag4 , onValueChange = {tag4=it})
                        Text(text = "5つめのタグ")
                        TextField(value =tag5 , onValueChange = {tag5=it})
                }
                })
        }
    }
}
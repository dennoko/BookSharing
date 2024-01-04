package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.booksharing.firestore.detailforapi

@Composable
fun bookdetaildisplay(detailforapi: detailforapi,navController: NavController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        var showDialog by remember { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = detailforapi.item.volumeInfo.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(32.dp))
            AsyncImage(model =detailforapi.item.volumeInfo.imageLinks
                , contentDescription = "apiで取ってきた本の画像")
        }
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "著者：　${detailforapi.item.volumeInfo.authors}")
            Text(text = "出版社：　${detailforapi.item.volumeInfo.publisher}")
            Text(text = "出版年：　${detailforapi.item.volumeInfo.publishedDate}")
            Text(text = "カテゴリ：　${detailforapi.item.volumeInfo.categories}")
            Text(text = "解説：　${detailforapi.item.volumeInfo.description}")
            Spacer(modifier = Modifier.width(32.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(onClick = { navController.navigate("home") },) {
                    Text(text = "トップページに戻る")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { showDialog = true },
                ) {
                    Text(text = "この本を削除する")
                }
            }
        }
        //ダイアログの表示
        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "キャンセル")
                    }
                },
                title = {
                    Text(text = "確認")
                },
                text = { Text(text = "本当にこの本を削除しますか?") })
        }
    }
}
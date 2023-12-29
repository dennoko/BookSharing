package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booksharing.testData.TestBooksData

@Composable
fun BookDisplay(testData: TestBooksData) {
    /*
    このコンポーザブルは、ホーム画面に本を表示するためのコンポーザブルです。
     */
    Column {
        // 本の画像を表示
        AsyncImage(model = testData.item.volumeInfo.imageLinks.thumbnail, contentDescription = null)

        Text(text = testData.item.volumeInfo.title)
    }
}
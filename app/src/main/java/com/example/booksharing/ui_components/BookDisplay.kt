package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.booksharing.firestore.detailforapi
import com.example.booksharing.testData.TestBooksData

@Composable
fun BookDisplay(testData: detailforapi) {
    /*
    このコンポーザブルは、ホーム画面に本を表示するためのコンポーザブルです。
     */
    Column {
        // 本の画像を表示
        AsyncImage(
            model = testData.item.volumeInfo.imageLinks.thumbnail,
            modifier = Modifier.size(100.dp),
            contentDescription = null
        )
    }
}
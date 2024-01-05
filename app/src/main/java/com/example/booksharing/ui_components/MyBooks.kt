package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booksharing.firestore.detailforapi

@Composable
fun MyBooks(book: detailforapi) {
    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            // 本の画像を表示
            BookDisplay(book)
            Spacer(modifier = Modifier.width(10.dp))

            // 本のタイトルと著者名を表示
            Column {
                Text(
                    text = book.item.volumeInfo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                book.item.volumeInfo.authors.forEach {
                    Text(text = it)
                }
            }
        }
    }
}
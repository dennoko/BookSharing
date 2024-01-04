package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booksharing.testData.TestBooksData
import com.example.booksharing.ui.theme.BookSharingTheme

@Composable
fun BookDisplayDetail(testData: TestBooksData) {
    Column {
        Row {
            AsyncImage(
                model = testData.item.volumeInfo.imageLinks.thumbnail,
                modifier = Modifier.size(200.dp),
                contentDescription = null
            )
            
            Column {
                Text(text = testData.item.volumeInfo.title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))

                testData.item.volumeInfo.authors.forEach {
                    Text(text = it)
                }
                Text(text = "所有者：" + testData.owner)
                Text(text = "貸出状況" + testData.borrower)
                Button(onClick = { /*TODO*/ }) { //予約ボタン
                    Text(text = "予約する")
                }
            }
        }

        // Buttonでtagの表示をしたい
        Text(text = "出版社：" + testData.item.volumeInfo.publisher)
        Text(text = "出版年：" + testData.item.volumeInfo.publishedDate)
        Text(text = "カテゴリ：" + testData.item.volumeInfo.categories.forEach { Text(text = it) })
        Text(text = "ISBN：" + testData.isbn)
        Text(text = "解説：\n" + testData.item.volumeInfo.description)
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewBookDisplayDetail () {
    BookSharingTheme() {
        BookDisplayDetail(testData = TestBooksData())
    }
}
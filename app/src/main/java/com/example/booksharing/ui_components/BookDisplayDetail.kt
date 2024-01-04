package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.booksharing.testData.TestBooksData
import com.example.booksharing.ui.theme.BookSharingTheme

@Composable
fun BookDisplayDetail(testData: TestBooksData) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = testData.item.volumeInfo.imageLinks.thumbnail,
                    modifier = Modifier
                        .size(200.dp),
                    contentDescription = null
                )

                Column {
                    Text(
                        text = testData.item.volumeInfo.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    testData.item.volumeInfo.authors.forEach {
                        Text(text = it)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "所有者：" + testData.owner)
                    Text(text = "貸出状況:" + testData.borrower)
                    Spacer(modifier = Modifier.height(15.dp))

                    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) { //予約ボタン
                        Text(text = "予約する", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "出版社：" + testData.item.volumeInfo.publisher)
            Text(text = "出版年：" + testData.item.volumeInfo.publishedDate)
            Text(text = "カテゴリ： ${testData.item.volumeInfo.categories}")
            Text(text = "ISBN：" + testData.isbn)
            Row {// tag2, tag3の表示をまとめてしたい
                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Text(text = testData.tag1)
                }
                Spacer(modifier = Modifier.width(5.dp))
                FilledTonalButton(onClick = { /*TODO*/ }) {
                    Text(text = testData.tag2)
                }
            }
            Text(text = "解説：\n" + testData.item.volumeInfo.description)
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewBookDisplayDetail () {
    BookSharingTheme() {
        BookDisplayDetail(testData = TestBooksData())
    }
}
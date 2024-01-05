package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    Spacer(modifier = Modifier.height(10.dp))

            Text(text = "出版社：" + testData.item.volumeInfo.publisher)
            Text(text = "出版年：" + testData.item.volumeInfo.publishedDate)
            Text(text = "ISBN：" + testData.isbn)
            Row {// tag2, tag3の表示をまとめてしたい
                FilledTonalButton(
                    modifier = Modifier.heightIn(min = 36.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = testData.tag1)
                }
                Spacer(modifier = Modifier.width(5.dp))
                FilledTonalButton(
                    modifier = Modifier.heightIn(min = 36.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = testData.tag2)
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "解説：\n" + testData.item.volumeInfo.description)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedIconButton( // 戻るボタン
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "削除ボタン")
            }
        }
    }
}}}
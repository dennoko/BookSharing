package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookDisplay(title: String) {
    /*
    このコンポーザブルは、ホーム画面に本を表示するためのコンポーザブルです。
     */
    Column {
        Card(
            modifier = Modifier
                .size(60.dp, 100.dp)
        ){
            Text(text = "Image")
        }

        Text(text = title)
    }
}
package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booksharing.testData.TestBooksData
import com.example.booksharing.ui.theme.BookSharingTheme

@Composable
fun SearchBox() {
    var text by remember {
        mutableStateOf("")
    }

    Column ( modifier = Modifier.padding(20.dp)) {
        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                ) },
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "本を検索") },
            singleLine = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBox () {
    BookSharingTheme {
        SearchBox()
    }
}
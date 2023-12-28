package com.example.booksharing.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.BookDisplay

@Composable
fun HomeScreen() {
    /*
    この画面がアプリのホーム画面になります。
    kindleのように、firebaseから取得した本の情報を表示できるようにしましょう。
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // ここに検索ボックスを作成します。 BasicTextField を使って作ってみましょう。


        // ここから本の情報を表示
        // タグごとに表示するようするので LazyColumn にタグのリストを渡します。
        // テスト用のデータ Todo データ取得が実装出来たら置き換え
        val tags = listOf("タグ1", "タグ2", "タグ3", "タグ4", "タグ5", "タグ6", "タグ7", "タグ8", "タグ9", "タグ10")

        LazyColumn {
            items(tags.size) {
                // タグを元に本の情報を取得
                // テスト用のデータ Todo データ取得が実装出来たら置き換え
                val books = listOf("本1", "本2", "本3", "本4", "本5", "本6", "本7", "本8", "本9", "本10")

                // タグ名を表示
                Text(text = tags[it])

                // LazyRow に本の情報を渡し、表示する
                LazyRow {
                    items(books.size) {
                        // 本の情報を表示
                        BookDisplay(title = books[it])
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                // 区切り線を表示
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    BookSharingTheme {
        HomeScreen()
    }
}
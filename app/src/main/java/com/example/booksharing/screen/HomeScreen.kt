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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksharing.ViewModel.HomeViewModel
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.BookDisplay

@Composable
fun HomeScreen(vm: HomeViewModel = viewModel()) {
    /*
    この画面がアプリのホーム画面になります。
    kindleのように、firebaseから取得した本の情報を表示できるようにしましょう。
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 本の情報を更新
        LaunchedEffect(Unit) {
            vm.getBooksTest()
        }

        // ここに検索ボックスを作成します。 BasicTextField を使って作ってみましょう。


        // ここから本の情報を表示
        // タグごとに表示するようするので LazyColumn にタグのリストを渡します。
        // テスト用のデータ Todo データ取得が実装出来たら置き換え
        val tags = listOf("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10")

        LazyColumn {
            items(tags.size) {
                // タグを元に本の情報を取得
                // テスト用のデータ Todo データ取得が実装出来たら置き換え
                val books = vm.booksList.collectAsState()

                // タグ名を表示
                Text(text = tags[it])

                // LazyRow に本の情報を渡し、表示する
                LazyRow {
                    items(books.value.size) {
                        // 本の情報を表示
                        BookDisplay(books.value[it])
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
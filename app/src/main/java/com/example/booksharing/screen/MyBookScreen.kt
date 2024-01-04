package com.example.booksharing.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.booksharing.ViewModel.MyBooksViewModel
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.BookDisplay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyBooksScreen(vm: MyBooksViewModel = viewModel()) {
    // 自分の本のリストとタグのリストを取得する
    LaunchedEffect(Unit) {
        vm.getMyBooks()
        vm.getTags()
    }

    // 自分の本のリストを保持する変数
    val myBooks = vm.myBooksList.collectAsState()
    // タグのリストを保持する変数
    val tags = vm.tagsList.collectAsState()

    // 検索した本のリストを保持する変数
    val searchedBooks = vm.searchedBooksData.collectAsState()

    // keyboardの表示を切り替える変数
    var keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            floatingActionButton = {
                // 円形の＋ボタンを表示する. +はIconsのaddを使う.
                FloatingActionButton(onClick = { vm.isShowDialog.value = true }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "追加ボタン",
                    )
                }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
            ) {
                // 検索ボックスの表示
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "検索アイコン",
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.padding(16.dp))

                    TextField(value = vm.keyWord.value,
                        onValueChange = {vm.keyWord.value = it},
                        placeholder = { Text(text = "検索ワードを入力") },
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            vm.searchBooks()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ) {
                        Text(text = "検索")
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))

                // 本の情報を表示
                Text(text = "My Books", fontSize = 20.sp)
                if(myBooks.value != null){
                    LazyColumn {
                        items(myBooks!!.value!!.size) {
                            BookDisplay(testData = myBooks!!.value!![it])
                        }
                    }
                }
            }

            if(vm.isShowDialog.value && searchedBooks.value != null) {
                AlertDialog(
                    onDismissRequest = { vm.isShowDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = { /*本を登録*/ }) {
                            Text(text = "追加")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { vm.isShowDialog.value = false }) {
                            Text(text = "キャンセル")
                        }
                    },
                    title = {
                        Text(text = "本の追加")
                    },
                    text = {
                        Column {
                            Text("追加する本を選択してください")
                            LazyColumn {
                                items(searchedBooks!!.value!!.items.size) {
                                    Column {
                                        AsyncImage(
                                            model = searchedBooks!!.value!!.items[it].volumeInfo.imageLinks.thumbnail,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clickable { vm.isShowBookOptions.value != vm.isShowBookOptions.value }
                                        )

                                        // 画像選択時にタグの選択とかをして、追加ボタンを表示する
                                        if(vm.isShowBookOptions.value) {

                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyBooksScreen() {
    BookSharingTheme {
        MyBooksScreen()
    }
}
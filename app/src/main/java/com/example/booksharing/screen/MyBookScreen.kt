package com.example.booksharing.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.booksharing.ViewModel.MyBooksViewModel
import com.example.booksharing.firestore.detaildata
import com.example.booksharing.firestore.detailforapi
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.BookDisplay

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyBooksScreen(vm: MyBooksViewModel = viewModel(), navController: NavController) {
    val userDB = AppDatabase.getDB(LocalContext.current)

    // 自分の本のリストとタグのリストを取得する
    LaunchedEffect(Unit) {
        vm.getMyBooks()
        vm.getTags()

        // room の id = 0 にデータが保存されているかどうかを確認し、なければユーザー情報を入力する画面を表示する. また、画面に表示する情報を取得する.
        val userData = userDB.userDataDao().getUserData()
        if (userData.userName == null) {
            navController.navigate("home")
        } else {
            vm.owner = userData.userName
        }
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
                        placeholder = { Text(text = "キーワードを入力") },
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
                        items(myBooks.value!!.size) {
                            BookDisplay(testData = myBooks.value!![it])
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
                                items(searchedBooks.value!!.items.size) {
                                    var isShowBookOptions by remember { mutableStateOf(false) }

                                    Column {
                                        AsyncImage(
                                            model = searchedBooks.value!!.items[it].volumeInfo.imageLinks.thumbnail,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clickable {
                                                    isShowBookOptions = !isShowBookOptions
                                                    Log.d("methodTest", "isShowBookOptions: $isShowBookOptions")
                                                }
                                        )

                                        // 画像選択時にタグの選択とかをして、追加ボタンを表示する
                                        if(isShowBookOptions) {
                                            // タグの表示
                                            if(tags.value != null) {
                                                LazyRow {
                                                    items(tags.value!!.size) {
                                                        var isSelect by remember { mutableStateOf(false) }
                                                        FilterChip(
                                                            selected = isSelect,
                                                            onClick = {
                                                                isSelect = !isSelect
                                                                // 選択されたときは、タグを追加し、選択が外されたときは、タグを削除する
                                                                if(isSelect) {
                                                                    vm.setTag(tags.value!![it])
                                                                } else {
                                                                    vm.removeTag(tags.value!![it])
                                                                }
                                                            },
                                                            label = { Text(text = tags.value!![it]) })
                                                    }
                                                }
                                            }

                                            // 新しいタグを追加する
                                            Row {
                                                TextField(value = vm.newTag.value,
                                                    onValueChange = {vm.newTag.value = it},
                                                    placeholder = { Text(text = "新しいタグを追加") },
                                                    modifier = Modifier
                                                        .weight(1f)
                                                )
                                                Spacer(modifier = Modifier.width(16.dp))

                                                Button(
                                                    onClick = {
                                                        vm.addNewTag()
                                                    }
                                                ) {
                                                    Text(text = "追加")
                                                }
                                            }

                                            // 本を追加するボタン
                                            Button(
                                                onClick = {
                                                    // isbn がない場合は、追加できないようにする
                                                    if(searchedBooks.value!!.items[it].volumeInfo.industryIdentifiers == null) {
                                                        // TODO: ISBN が無く、追加できないことをユーザーに伝える
                                                        return@Button
                                                    } else {
                                                        // 追加する本のデータを含むdetailforapiを作成する
                                                        val addedBookInfo = detailforapi(
                                                            detail = detaildata(
                                                                owner = vm.owner,
                                                                isbn = searchedBooks.value!!.items[it].volumeInfo.industryIdentifiers!![0].identifier,
                                                                tag1 = vm.tag1.value,
                                                                tag2 = vm.tag2.value,
                                                                tag3 = vm.tag3.value,
                                                                tag4 = vm.tag4.value,
                                                                tag5 = vm.tag5.value
                                                            ),
                                                            item = searchedBooks.value!!.items[it]
                                                        )

                                                        vm.addBook(addedBookInfo)
                                                        vm.isShowDialog.value = false
                                                    }
                                                }
                                            ) {
                                                Text(text = "追加")
                                            }
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
    val navController = rememberNavController()

    BookSharingTheme {
        MyBooksScreen(navController = navController)
    }
}
package com.example.booksharing.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
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
import com.example.booksharing.ui_components.BookDisplayDetail
import com.example.booksharing.ui_components.LoadingIndicator
import com.example.booksharing.ui_components.MyBooks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyBooksScreen(vm: MyBooksViewModel = viewModel(), navController: NavController) {
    val userDB = AppDatabase.getDB(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // 自分の本のリストとタグのリストを取得する
    LaunchedEffect(Unit) {
        vm.getTags()

        // room の id = 0 にデータが保存されているかどうかを確認し、なければユーザー情報を入力する画面を表示する. また、画面に表示する情報を取得する.
        val userData = userDB.userDataDao().getUserData()
        if (userData.userName == null) {
            navController.navigate("home")
        } else {
            vm.owner = userData.userName

            vm.getMyBooks()
        }
    }

    // 自分の本のリストを保持する変数
    val myBooks = vm.myBooksList.collectAsState()
    // タグのリストを保持する変数
    val tags = vm.tagsList.collectAsState()

    // 検索した本のリストを保持する変数
    val searchedBooks = vm.searchedBooksData.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
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
                // 本の情報を表示
                Text(
                    text = "My Books",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(thickness = 2.dp)

                if(myBooks.value != null){
                    LazyColumn {
                        items(myBooks.value!!.size) {
                            MyBooks(myBooks.value!![it])

                            Divider()
                        }
                    }
                } else {
                    LoadingIndicator()
                }
            }

            if(vm.isShowDialog.value) {

                AlertDialog(
                    modifier = Modifier
                        .sizeIn(maxHeight = 600.dp, maxWidth = 400.dp),
                    onDismissRequest = { vm.isShowDialog.value = false },
                    confirmButton = {
                        TextButton(onClick =  { vm.isShowDialog.value = false }) {
                            Text(text = "閉じる")
                        }
                    },
                    title = {
                        Text(text = "本を登録する", fontWeight = FontWeight.SemiBold)
                    },
                    text = {
                        Column {
                            // 検索ボックスの表示
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val keyboardController = LocalSoftwareKeyboardController.current
                                val focusManager = LocalFocusManager.current

                                TextField(
                                    value = vm.keyWord.value,
                                    onValueChange = {vm.keyWord.value = it},
                                    label = { Text("キーワードを入力") },
                                    modifier = Modifier
                                        .weight(1f),
                                    singleLine = true,
                                    trailingIcon = { // 本の検索ボタン
                                        IconButton(
                                            onClick = {
                                                if(vm.keyWord.value != "") {
                                                    vm.searchBooks()
                                                } else {
                                                    Toast.makeText(context, "検索ワードを入力してください", Toast.LENGTH_SHORT).show()
                                                }

                                                coroutineScope.launch {
                                                    withContext(Dispatchers.Default) {
                                                        keyboardController?.hide()
                                                        focusManager.clearFocus()
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = "本の検索アイコン",
                                                tint = MaterialTheme.colorScheme.primary,
                                            )
                                        }
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn {
                                if(searchedBooks.value != null) {
                                    items(searchedBooks.value!!.items.size) {
                                        var isShowBookOptions by remember { mutableStateOf(false) }

                                        Column {
                                            Row(
                                                modifier = Modifier
                                                    .clickable { isShowBookOptions = !isShowBookOptions }
                                            ) {
                                                AsyncImage(
                                                    modifier = Modifier.size(80.dp),
                                                    model = searchedBooks.value!!.items[it].volumeInfo.imageLinks.thumbnail,
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Fit,
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                
                                                Text(text = searchedBooks.value!!.items[it].volumeInfo.title,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                )
                                            }

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
                                                                label = { Text(text = tags.value!![it]) }
                                                            )
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                        }
                                                    }
                                                }

                                                // 新しいタグを追加する
                                                Row {
                                                    OutlinedTextField(
                                                        value = vm.newTag.value,
                                                        onValueChange = {vm.newTag.value = it},
                                                        placeholder = { Text(text = "新しいタグを追加") },
                                                        modifier = Modifier
                                                            .weight(1f),
                                                        leadingIcon = {
                                                            IconButton(
                                                                onClick = { vm.addNewTag() }
                                                            ) {
                                                                Icon(
                                                                    imageVector = Icons.Default.Add,
                                                                    contentDescription = "タグ追加ボタン"
                                                                )
                                                            }
                                                        }
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(6.dp))

                                                // 本を追加するボタン
                                                Button(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    onClick = {
                                                        // isbn がない場合は、追加できないようにする
                                                        if(searchedBooks.value!!.items[it].volumeInfo.industryIdentifiers == null) {
                                                            Toast.makeText(context, "ISBNがありません", Toast.LENGTH_SHORT).show()
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
                                                    Text(text = "登録する")
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))
                                            Divider()
                                            Spacer(modifier = Modifier.height(8.dp))
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
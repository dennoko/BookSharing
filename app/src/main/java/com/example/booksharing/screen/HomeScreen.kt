package com.example.booksharing.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.booksharing.ViewModel.HomeViewModel
import com.example.booksharing.firestore.detailforapi
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.ui.theme.BookSharingTheme
import com.example.booksharing.ui_components.BookDisplay
import com.example.booksharing.ui_components.BookDisplayDetail
import com.example.booksharing.ui_components.SearchBox
import com.google.common.collect.ImmutableList
import com.google.common.math.LinearTransformation.horizontal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun HomeScreen(vm: HomeViewModel = viewModel(), navController: NavController) {
    /*
    この画面がアプリのホーム画面になります。
    kindleのように、firebaseから取得した本の情報を表示できるようにしましょう。
     */
    val coroutineScope = rememberCoroutineScope()
    // get context
    val context = LocalContext.current

    // Room のインスタンスを作成
    val db = AppDatabase.getDB(context)

    // ユーザーネームを保持する変数
    var userName = ""

    // room の id = 0 に保存されたデータがあるかどうかを確認し、なければユーザー情報を入力する画面を表示する. また、画面に表示する情報を取得する.
    var isShowInputUserInfoScreen by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val userData = db.userDataDao().getUserData()
            if (userData == null) {
                isShowInputUserInfoScreen = true
            } else {
                userName = userData.userName!!
            }

            // 画面に表示する情報の取得
            vm.getTags()
        }
    }

    // タグのリストを取得する
    val tagsList = vm.tagsList.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 本の情報を更新 TODO: テスト用のコードなので、データ取得が実装出来たら置き換え
        LaunchedEffect(Unit) {
            vm.getBooksTest()
        }

        // ここに検索ボックスを作成します。
        SearchBox()
        Spacer(modifier = Modifier.height(8.dp))

        // ここから本の情報を表示
        // タグごとに表示するようするので LazyColumn にタグのリストを渡します。
        val tags = tagsList.value ?: emptyList<String>()

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            tags.forEach() {
                // タグごとの本のリストを取得
                var books: ImmutableList<detailforapi>? by remember { mutableStateOf(null) }


                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            books = vm.getBooks(it)
                        }
                    }
                }

                // タグ名を表示
                Text(text = it, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))

                // LazyRow に本の情報を渡し、表示する
                LazyRow {
                    if(books != null) {
                        Log.d("hoge", "HomeScreen LazyRow booksData.value != null: ${books}")
                        items(books!!.size) {
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        // 詳細表示
                                        vm.selectedBookInfo.value = books!![it]
                                        vm.isShowBookDetail.value = true
                                    }
                            ) {
                                // 本の情報を表示
                                BookDisplay(books!![it])
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))

                // 区切り線を表示
                Divider()
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }

    // 本の詳細を表示
    if (vm.isShowBookDetail.value && vm.selectedBookInfo.value != null) {
        AlertDialog(
            modifier = Modifier
                .padding(24.dp)
                .sizeIn(maxHeight = 600.dp, maxWidth = 400.dp),
            onDismissRequest = { vm.isShowBookDetail.value = false },
            confirmButton = {
                TextButton(onClick = { vm.isShowBookDetail.value = false }) {
                    Text(text = "閉じる")
                }
            },
            text = {
                BookDisplayDetail(bookData = vm.selectedBookInfo.value!!) {
                    // 予約ボタンを押した時の処理
                    vm.selectedBookInfo.value!!.item.volumeInfo.industryIdentifiers?.get(0)?.let {
                        if(userName != "") {
                            vm.registBrowwer(
                                owner = vm.selectedBookInfo.value!!.detail.owner,
                                isbn = it.identifier,
                                borrower = userName
                            )

                            // トーストを表示
                            Toast.makeText(context, "予約しました", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run{
                        // トーストを表示
                        Toast.makeText(context, "予約に失敗しました", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    // 初回起動時にユーザー情報を入力する画面を表示する
    if (isShowInputUserInfoScreen) {
        InputUserInfoScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()

    BookSharingTheme {
        HomeScreen(navController = navController)
    }
}
package com.example.booksharing.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.booksharing.ViewModel.HomeViewModel
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.room.UserDataEntity
import com.example.booksharing.ui.theme.BookSharingTheme
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputUserInfoScreen(vm: HomeViewModel = viewModel(), navController: NavController) {
    // get context
    val context = LocalContext.current
    // Roomのインスタンスを作成
    val db = AppDatabase.getDB(context)

    // ユーザー名を保持する変数
    var userName by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    // TextFieldの外がタップされたら、フォーカスを外し、キーボードを閉じる
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // 登録済みのユーザー情報を取得
    val usersList = remember { mutableStateOf<ImmutableList<String>?>(null) }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                vm.getUsersList()

                usersList.value = vm.usersList.value
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { focusManager.clearFocus(); keyboardController?.hide() }
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "ようこそ\nC0de書籍管理アプリへ",
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(210.dp))

        Icon(
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary,
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "IconImage"
        )
        Spacer(modifier = Modifier.height(34.dp))
        
        // ここにユーザー情報を入力する画面を作成します。
        TextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Create, tint = MaterialTheme.colorScheme.primary,contentDescription = null) },
            label = {
                Text("ユーザー名")
            },
            singleLine = true,
            placeholder = { Text("例: dennoko") },
        )
        Spacer(modifier = Modifier.height(14.dp))

        // ユーザー情報の保存ボタン
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = {
                coroutineScope.launch {
                    // 既存のユーザーと重複していないか確認
                    if (usersList.value?.contains(userName) == true) {
                        // 重複している場合は、ユーザー名を変更するように促す
                        Log.d("methodTest", "InputUserInfoScreen: 既存のユーザーと重複しています")
                        Toast.makeText(context, "既存のユーザーと重複しています", Toast.LENGTH_SHORT).show()
                    } else if(userName == "") {
                        // ユーザー名が空の場合は、ユーザー名を入力するように促す
                        Log.d("methodTest", "InputUserInfoScreen: ユーザー名を入力してください")
                        Toast.makeText(context, "ユーザー名を入力してください", Toast.LENGTH_SHORT).show()
                    } else {
                        withContext(Dispatchers.IO) {
                            try {
                                db.userDataDao().insertUserData(UserDataEntity(id = 0,userName))
                                focusManager.clearFocus()
                                withContext(Dispatchers.Main) { // キーボードを閉じるのと画面遷移はメインスレッドで行ったほうがいいのか？
                                    keyboardController?.hide()
                                    navController.navigate("home")
                                }
                            } catch (e: Exception) {
                                Log.d("methodTest", "insertUserData: error ${e.message}  ${e.cause}")
                            }
                        }
                    }
                }
            }
        ) {
            Text(text = "登録", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputUserInfoScreen () {
    val navController = rememberNavController()
    BookSharingTheme() {
        InputUserInfoScreen(navController = rememberNavController())
    }
}
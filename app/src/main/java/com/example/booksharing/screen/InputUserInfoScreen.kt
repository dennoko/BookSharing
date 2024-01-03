package com.example.booksharing.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.room.UserDataEntity
import com.example.booksharing.ui.theme.BookSharingTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputUserInfoScreen() {
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { focusManager.clearFocus(); keyboardController?.hide() }
    ) {
        // ここにユーザー情報を入力する画面を作成します。
        TextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            label = {
                Text("ユーザー名")
            },
            singleLine = true,
            placeholder = { Text("例: dennoko") }
        )

        // ユーザー情報の保存ボタン
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        db.userDataDao().insertUserData(UserDataEntity(id = 0,userName))
                    } catch (e: Exception) {
                        Log.d("methodTest", "insertUserData: error ${e.message}  ${e.cause}")
                    }
                }
            }
        ) {
            Text(text = "Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputUserInfoScreen() {
    BookSharingTheme {
        InputUserInfoScreen()
    }
}
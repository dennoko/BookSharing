package com.example.booksharing.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.booksharing.ViewModel.HomeViewModel
import com.example.booksharing.room.AppDatabase
import com.example.booksharing.room.UserDataEntity
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
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { focusManager.clearFocus(); keyboardController?.hide() }
            .background(color = MaterialTheme.colorScheme.surface)
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
                                keyboardController?.hide()
                            } catch (e: Exception) {
                                Log.d("methodTest", "insertUserData: error ${e.message}  ${e.cause}")
                            }
                        }
                    }
                }
            }
        ) {
            Text(text = "Save")
        }
    }
}
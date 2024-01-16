package com.example.booksharing.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksharing.ViewModel.HomeViewModel

@Composable
fun TestScreen(vm: HomeViewModel = viewModel()) {
    // この画面はいろいろ試すための画面です。本番では使用しません。
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = { vm.registBrowwer("C0de", "4774143944") }) {

        }
    }
}
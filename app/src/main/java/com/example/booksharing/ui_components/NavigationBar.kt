package com.example.booksharing.ui_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigationBar(navController: NavController) {
    /*
    このコンポーザブルは、アプリの下部に表示されるナビゲーションバーです。
    このコンポーザブルは、画面遷移を行うためのコンポーザブルです。
     */
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        NavigateButton(navController, route = "home", txt = "Home", modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(horizontal = 4.dp))
        NavigateButton(navController, route = "myPage", txt = "MyPage", modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(horizontal = 4.dp))
        NavigateButton(navController, route = "setting", txt = "Setting", modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(horizontal = 4.dp))
    }
}

@Composable
fun NavigateButton(navController: NavController, route: String, txt: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { navController.navigate(route) },
        shape = RoundedCornerShape(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 15.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        modifier = modifier
    ) {
        Text(
            text = txt,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
    }
}
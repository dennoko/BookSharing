package com.example.booksharing.firestore

import com.example.booksharing.GoogleBooksAPI.Item
import javax.annotation.concurrent.Immutable

@Immutable
data class detaildata(
    val isbooked:Boolean,
    val owner:String,
    val borrower:String,
    val isbn:String,
    val tag1:String,
    val tag2:String,
    val tag3:String,
    val tag4:String,
    val tag5:String
)
@Immutable
data class detailforapi(
    val detail:detaildata,
    val item:Item
)

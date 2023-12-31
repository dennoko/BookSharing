package com.example.booksharing.firestore

import com.example.booksharing.GoogleBooksAPI.Item

data class detaildata(
    var isbooked:Boolean,
    var owner:String,
    var borrower:String,
    var isbn:String,
    var tag1:String,
    var tag2:String,
    var tag3:String,
    var tag4:String,
    var tag5:String
)

data class detailforapi(
    var detail:detaildata,
    var item:Item
)

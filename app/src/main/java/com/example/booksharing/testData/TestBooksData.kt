package com.example.booksharing.testData

import com.example.booksharing.GoogleBooksAPI.ImageLinks
import com.example.booksharing.GoogleBooksAPI.IndustryIdentifier
import com.example.booksharing.GoogleBooksAPI.Item
import com.example.booksharing.GoogleBooksAPI.VolumeInfo

// UIに渡すデータを保持するデータクラス
data class TestBooksData(
    val owner: String = "dennoko",
    val isbn: String = "9784973114895",
    val booked: Boolean = false,
    val borrower: String = "",
    val tag1: String = "tag1",
    val tag2: String = "tag2",
    val tag3: String = "tag3",
    val tag4: String = "",
    val tag5: String = "",

    val item: Item = Item(
        id = "OfN_7hj2t5wC",
        selfLink = "https://www.googleapis.com/books/v1/volumes/OfN_7hj2t5wC",
        volumeInfo = VolumeInfo(
            title = "初めてのAndroid",
            authors = listOf("エドバーネット", "あい"),
            publisher = "オライリー・ジャパン",
            publishedDate = "2009-05-15",
            description = "Androidの基本を学ぶための入門書。Androidの概要から、開発環境の構築、アプリケーションの開発、デバッグ、公開までを解説。Androidの基本を学ぶための入門書。Androidの概要から、開発環境の構築、アプリケーションの開発、デバッグ、公開までを解説。",
            industryIdentifiers = listOf(
                IndustryIdentifier(
                    type = "ISBN_10",
                    identifier = "4873113930"
                ),
                IndustryIdentifier(
                    type = "ISBN_13",
                    identifier = "9784873113933"
                )
            ),
            categories = listOf("Computers", "C0de"),
            imageLinks = ImageLinks(
                thumbnail = "http://books.google.com/books/content?id=OfN_7hj2t5wC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            )
        )
    )
)

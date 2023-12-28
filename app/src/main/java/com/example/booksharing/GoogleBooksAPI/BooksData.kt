package com.example.booksharing.GoogleBooksAPI

import kotlinx.serialization.Serializable

@Serializable
data class BooksData(
    val items: List<Item>
)

@Serializable
data class Item(
    val id: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val industryIdentifiers: List<IndustryIdentifier>,
    val categories: List<String>,
    val imageLinks: ImageLinks
)

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

@Serializable
data class ImageLinks(
    val thumbnail: String
)

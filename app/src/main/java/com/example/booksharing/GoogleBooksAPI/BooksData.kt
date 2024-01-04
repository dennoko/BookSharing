package com.example.booksharing.GoogleBooksAPI

import kotlinx.serialization.Serializable

@Serializable
data class BooksData(
    val items: List<Item>
)

@Serializable
data class Item(
    val id: String,
    val selfLink: String = "",
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String = "No Title",
    val authors: List<String> = emptyList(),
    val publisher: String = "No Publisher",
    val publishedDate: String = "No Published Date",
    val description: String = "No Description",
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val imageLinks: ImageLinks = ImageLinks()
)

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

@Serializable
data class ImageLinks(
    val thumbnail: String = "https://t4.ftcdn.net/jpg/04/75/01/23/240_F_475012363_aNqXx8CrsoTfJP5KCf1rERd6G50K0hXw.jpg"
)

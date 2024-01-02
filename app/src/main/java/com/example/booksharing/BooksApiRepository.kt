package com.example.booksharing.GoogleBooksAPI

class BooksApiRepository {
    private val apiService = RetrofitInstance.apiService

    suspend fun searchBooks(query: String): BooksData {
        return apiService.searchBooks(query)
    }
}
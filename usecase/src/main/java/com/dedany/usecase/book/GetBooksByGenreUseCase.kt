package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetBooksByGenreUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {

    suspend operator fun invoke(genre: String): List<Book> {
        return bookRepository.getBooksByGenre(genre)
    }
}
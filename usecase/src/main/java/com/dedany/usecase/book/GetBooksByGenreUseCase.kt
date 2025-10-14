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
    suspend operator fun invoke(genre: String): List<Book> = coroutineScope {
        // 1️⃣ Traemos todos los libros (aunque no tengan subjects)
        val allBooks = bookRepository.getAllBooks()
        val semaphore = Channel<Unit>(capacity = 10) // máximo 10 peticiones simultáneas

        val detailedBooks = allBooks.map { book ->
            async {
                semaphore.send(Unit)
                try {
                    if (book.subjects.isNullOrEmpty()) {
                        bookRepository.getBookDetail(book.id)
                    } else {
                        book
                    }
                } finally {
                    semaphore.receive()
                }
            }
        }.awaitAll()

        detailedBooks.filter { book ->
            book.subjects?.any { it.equals(genre, ignoreCase = true) } == true
        }
    }
}
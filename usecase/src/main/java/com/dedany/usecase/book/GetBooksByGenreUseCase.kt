package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksByGenreUseCase @Inject constructor (
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(genre: String): List<Book> {
        // 1️⃣ Obtiene todos los libros (pueden venir de Room o red, según tu repo)
        val allBooks = bookRepository.getAllBooks()

        return allBooks.filter { book ->
            book.subjects?.any { subject ->
                subject.equals(genre, ignoreCase = true)
            } == true
        }
    }
}

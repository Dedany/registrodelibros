package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor (private val repository: BookRepository) {
    suspend operator fun invoke(id: String) : Book? = repository.getBookById(id)
}

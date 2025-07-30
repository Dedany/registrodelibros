package com.dedany.usecase.author

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository

class GetAllAuthorsUseCase(private val repository: AuthorRepository) {
    suspend operator fun invoke(): List<Author> = repository.getAllAuthors()
}
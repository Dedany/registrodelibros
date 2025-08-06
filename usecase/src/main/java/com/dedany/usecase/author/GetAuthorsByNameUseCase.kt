package com.dedany.usecase.author

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import javax.inject.Inject

class GetAuthorsByNameUseCase  @Inject constructor (private val repository: AuthorRepository) {
    suspend operator fun invoke(name: String): List<Author> = repository.getAuthorsByName(name)
}
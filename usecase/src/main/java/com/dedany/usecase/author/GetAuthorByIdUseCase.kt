package com.dedany.usecase.author

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import javax.inject.Inject

class GetAuthorByIdUseCase  @Inject constructor (private val repository: AuthorRepository) {
    suspend operator fun invoke(id: String): Author? = repository.getAuthorById(id)
}
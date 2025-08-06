package com.dedany.usecase.author

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import javax.inject.Inject

class SaveAuthorUseCase @Inject constructor (private val repository: AuthorRepository) {
    suspend operator fun invoke(author: Author) : Unit = repository.saveAuthor(author)
}
package com.dedany.usecase.bookAuthor

import com.dedany.domain.repository.BookAuthorRepository
import javax.inject.Inject

class InsertCrossRefsUseCase  @Inject constructor (private val repository: BookAuthorRepository) {
    suspend operator fun invoke(crossRefs: List<Pair<String, String>>) = repository.insertCrossRefs(crossRefs)
}